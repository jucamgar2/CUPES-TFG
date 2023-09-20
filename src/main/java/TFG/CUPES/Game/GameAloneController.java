package TFG.CUPES.Game;

import java.io.IOException;
import java.util.List;
import java.util.Random;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public static final List<Integer> footballLogoPositions = List.of(0,500,1000,1500);

    private GameAloneService gameService;

    private ImageService logoService;

    @Autowired
    public GameAloneController(GameAloneService gameS, ImageService logoS){
        this.gameService = gameS;
        this.logoService = logoS;
    }

    @GetMapping("/select")
    public ModelAndView selectMode(){
        ModelAndView res = new ModelAndView(SELECT_MODE);
        return res;
    }

    @GetMapping("/new")
    public String createGame(){
        GameAlone g = new GameAlone();
        g.setSelected(this.logoService.getRandomLogo());
        g.setIsFinish(false);
        g.setShift(0);
        g.setWin(false);
        gameService.saveGame(g);
        return "redirect:/game/play/"+g.getId();
    }

    @GetMapping("/play/{id}")
    public ModelAndView playGame(@PathVariable("id") Integer id) {
        ModelAndView res = new ModelAndView(PLAY_GAME);
        GameAlone game = this.gameService.getGameById(id).orElse(null);
        if(game==null){
            return new ModelAndView("redirect:/welcome");
        }
        if(game!=null && game.getIsFinish()){
            res = gameResult(game);
        }else {
            Image logo = new Image();
            res.addObject("logo", logo);
            res.addObject("game",game);
            String imageSelected = "/images/"+game.getSelected().getImageType()+"/"+game.getSelected().getResourceName()+".png";
            res.addObject("imageUrl", imageSelected);

            String imageStyle = generateStyle(imageSelected, game);
    
            res.addObject("imageStyle", imageStyle);
        }
        return res;
    }


    @PostMapping("/play/{id}")
    public ModelAndView checkGame(@ModelAttribute("logo") Image logo,@PathVariable("id") Integer id){
        ModelAndView res = new ModelAndView("redirect:/welcome");
        GameAlone game = this.gameService.getGameById(id).orElse(null);
        if(game!=null){
            game.setShift(game.getShift()+1);
            if(logo.getName().equals(game.getSelected().getName())){
                game.setWin(true);
                game.setIsFinish(true);
                res = new ModelAndView("redirect:/game/play/"+game.getId());
            }else{
                if(game.getShift().equals(maxShift)){
                    game.setWin(false);
                    game.setIsFinish(true);
                }
                res = new ModelAndView("redirect:/game/play/"+game.getId());
            }
        }
        
        this.gameService.saveGame(game);
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

    public String generateStyle(String imageSelected,GameAlone game) {
        Random rand = new Random();
        int x = 999;
        int y = 999;
        while(game.getX()==null && game.getY()==null ||(game.getX()!=x && game.getY()!=y)){
            x = footballLogoPositions.get(rand.nextInt(0, footballLogoPositions.size()));
            y = footballLogoPositions.get(rand.nextInt(0, footballLogoPositions.size()));
            game.setX(x);
            game.setY(y);
        }
        gameService.saveGame(game);
        return "backgroud-color: white;background-image: url('" + imageSelected + "'); width: " + 500+"px; height: " + 500 + "px; background-position: -" + game.getX() + "px -" + game.getY() + "px;";
    }
}
