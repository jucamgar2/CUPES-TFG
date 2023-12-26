package TFG.CUPES.controllers;


import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import TFG.CUPES.components.GameUtils;
import TFG.CUPES.entities.GameAlone;
import TFG.CUPES.entities.Image;
import TFG.CUPES.entities.Player;
import TFG.CUPES.entities.Position;
import TFG.CUPES.services.GameAloneService;
import TFG.CUPES.services.ImageService;
import TFG.CUPES.services.PlayerService;
import TFG.CUPES.services.PositionService;

@Controller
@RequestMapping("/game")
public class GameAloneController {

    public static final String PLAY_GAME = "game/playGame";
    public static final String RES_GAME= "game/gameResult";
    public static final String SELECT_MODE="game/selectMode";
    public static final Integer maxShift = 4;
    GameUtils gameUtils = new GameUtils();

    private GameAloneService gameService;
    private ImageService logoService;
    private PlayerService playerService;
    private PositionService positionService;

    @Autowired
    public GameAloneController(GameAloneService gameS, ImageService logoS,PlayerService playerService, PositionService positionService){
        this.gameService = gameS;
        this.logoService = logoS;
        this.playerService = playerService;
        this.positionService = positionService;
    }

    @GetMapping("")
    public String selectMode(Model model) {
        return SELECT_MODE;
    }

    @GetMapping("/new")
    public String createGame(Principal principal){
        GameAlone g = new GameAlone();
        if(principal !=null){
            Player player = this.playerService.getByUsername(principal.getName());
            if(player!=null){
                g.setPlayer(player);
            }
        }
        String token = UUID.randomUUID().toString();
        
        g.setToken(token);
        g.setSelected(this.logoService.getRandomLogo());
        g.setIsFinish(false);
        g.setShift(0);
        g.setWin(false);
        gameService.saveGame(g);
        return "redirect:/game/play/" + g.getId() + "?token=" + token;
    }

    @GetMapping("/play/{id}")
    public ModelAndView playGame(@PathVariable("id") Integer id,@RequestParam(required = false) String token) throws IOException {
        ModelAndView res = new ModelAndView(PLAY_GAME);
        if(token == null){
           return gameUtils.expelPlayer();
        }
        GameAlone game = this.gameService.getGameByTokenAndId(token, id).orElse(null);
        if(game==null){
            return gameUtils.expelPlayer();
        }
        if(game!=null && game.getIsFinish()){
            res = gameResult(game);
        }else {
            Image logo = new Image();
            List<Position> positions = this.positionService.findAll();
            res.addObject("logo", logo);
            res.addObject("game",game);
            String imageSelected = "/images/"+game.getSelected().getImageType()+"/"+game.getSelected().getResourceName()+".jpg";
            res.addObject("imageUrl", imageSelected);
            Position p;
            if(game.getX()==null || game.getY()==null){
                p = new Position(0,0);
            }else{
                p = new Position(game.getX(),game.getY());
            }
            if(game.getPositions().size() == game.getShift()){
                p = gameUtils.randomImagePortion( game.getPositions(), positions);
                while(!gameUtils.checkImageHasMoreThan1Color(imageSelected, p)){
                    p = gameUtils.randomImagePortion( game.getPositions(), positions);
                }
                game.setX(p.getX());
                game.setY(p.getY());
                game.getPositions().add(p);
                
            }
            this.gameService.saveGame(game);
            String imageStyle = gameUtils.generateImageStyle(imageSelected, p);
            res.addObject("imageStyle", imageStyle);
            String fullImageStyle = gameUtils.generateImageStyle(positions, game.getPositions());
            res.addObject("fullImageStyle", fullImageStyle);
            
            if(game.getShift()>0){
                List<String> errors = new ArrayList<>();
                errors.add("¡Lo siento! Fallaste en tu último intento");
                res.addObject("errors", errors);
            }
            
        }
        return res;
    }


    @PostMapping("/play/{id}")
    public ModelAndView checkGame(@ModelAttribute("logo") Image logo,@PathVariable("id") Integer id,@RequestParam(required = false) String token){
        ModelAndView res = new ModelAndView("redirect:/");
        if(token == null){
            return gameUtils.expelPlayer();
        }
        GameAlone game = this.gameService.getGameByTokenAndId(token, id).orElse(null);
        if(game == null){
            return gameUtils.expelPlayer();
        }else{
            game.setShift(game.getShift()+1);
            if(logo.getName().equals(game.getSelected().getName())){
                game.setWin(true);
                game.setIsFinish(true);
                res= new ModelAndView("redirect:/game/play/" + game.getId() + "?token=" + token);
            }else{
                if(game.getShift().equals(maxShift)){
                    game.setWin(false);
                    game.setIsFinish(true);   
                }
                res = new ModelAndView("redirect:/game/play/" + game.getId() + "?token=" + token);
            }
            this.gameService.saveGame(game);
        }
        return res;
    }

    public ModelAndView gameResult(GameAlone game){
        ModelAndView res = new ModelAndView(RES_GAME);
        res.addObject("game", game);
        String imageSelected = "/images/"+game.getSelected().getImageType()+"/"+game.getSelected().getResourceName()+".jpg";
        res.addObject("imageUrl", imageSelected);
        if(game.getWin()){
            res.addObject("message","¡Acertaste! Efectivamente era el escudo del "+game.getSelected().getName() );
            res.addObject("message2", "¡Felicidades! Solo has necesitado " + game.getShift() + " intentos para acertar");
        }else{
            res.addObject("message","¡Lo siento! Has fallado, era el escudo del "+game.getSelected().getName() );
            res.addObject("message2", "¡Lo siento! Has gastado todos tus intentos y no has conseguido adivinar el equipo");
        }
        return res;
    }    

}
