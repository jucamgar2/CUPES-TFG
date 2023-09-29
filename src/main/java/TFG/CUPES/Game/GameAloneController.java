package TFG.CUPES.Game;

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

import TFG.CUPES.Image.Image;
import TFG.CUPES.Image.ImageService;

@Controller
@RequestMapping("/game")
public class GameAloneController {

    public static final String PLAY_GAME = "game/playGame";
    public static final String RES_GAME= "game/gameResult";
    public static final String SELECT_MODE="game/selectMode";
    public static final Integer maxShift = 3;
    GameUtils gameUtils = new GameUtils();

    private GameAloneService gameService;
    private ImageService logoService;

    @Autowired
    public GameAloneController(GameAloneService gameS, ImageService logoS){
        this.gameService = gameS;
        this.logoService = logoS;
    }

    @GetMapping("/select")
    public String selectMode(Model model) {
        return SELECT_MODE;
    }

    @GetMapping("/new")
    public String createGame(){
        String token = UUID.randomUUID().toString();
        GameAlone g = new GameAlone();
        g.setToken(token);
        g.setSelected(this.logoService.getRandomLogo());
        g.setIsFinish(false);
        g.setShift(0);
        g.setWin(false);
        gameService.saveGame(g);
        return "redirect:/game/play/" + g.getId() + "?token=" + token;
    }

    @GetMapping("/play/{id}")
    public ModelAndView playGame(@PathVariable("id") Integer id,@RequestParam(required = false) String token) {
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
            res.addObject("logo", logo);
            res.addObject("game",game);
            String imageSelected = "/images/"+game.getSelected().getImageType()+"/"+game.getSelected().getResourceName()+".png";
            res.addObject("imageUrl", imageSelected);
            Position p = new Position(game.getX(),game.getY());
            p = gameUtils.randomImagePortion(imageSelected, p);
            game.setX(p.getX());
            game.setY(p.getY());
            String imageStyle = gameUtils.generateImageStyle(imageSelected, p);
            res.addObject("imageStyle", imageStyle);
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
        String imageSelected = "/images/"+game.getSelected().getImageType()+"/"+game.getSelected().getResourceName()+".png";
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
