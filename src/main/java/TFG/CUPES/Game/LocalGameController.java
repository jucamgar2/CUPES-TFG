package TFG.CUPES.Game;

import java.time.LocalDateTime;
import java.util.UUID;
import java.io.IOException;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/localGame")
public class LocalGameController {

    public static final String NEW_LOCAL_GAME = "game/newLocalGame";
    public static final String PLAY_LOCAL_GAME = "game/playLocalGame";
    public static final String RES_LOCAL_GAME ="game/resLocalGame";
    GameUtils gameUtils = new GameUtils();  
    
    LocalGameService localGameService;
    ImageService imageService;

    @Autowired
    LocalGameController(LocalGameService localGameService, ImageService imageService){
        this.localGameService = localGameService;
        this.imageService = imageService;
    }

    @GetMapping("/new")
    public ModelAndView newLocalGame(){
        ModelAndView res = new ModelAndView(NEW_LOCAL_GAME);
        LocalGame lg = new LocalGame();
        res.addObject("localGame", lg);
        return res;
    }

    @PostMapping("/new")
    public String createLocalGame(@ModelAttribute("localGame") LocalGame localGame){
        localGame.setActualPlayer(localGame.getPlayer1Name());
        localGame.setPlayer1Image(imageService.getRandomLogo());
        localGame.setPlayer2Image(imageService.getRandomLogo());
        localGame.setPlayer1Shifts(0);
        localGame.setPlayer2Shifts(0);
        localGame.setPlayer1Start(LocalDateTime.now());
        String token = UUID.randomUUID().toString();
        localGame.setToken(token);
        this.localGameService.save(localGame);
        return "redirect:/localGame/play/"+localGame.getId() + "?token=" + token;
    }

    @GetMapping("/play/{id}")
    public ModelAndView playGame(@PathVariable("id") Integer id,@RequestParam(required = false) String token) throws IOException{
        ModelAndView res = new ModelAndView(PLAY_LOCAL_GAME);
        if(token ==null){
            return gameUtils.expelPlayer();
        }
        LocalGame game = this.localGameService.getLocalGameByTokenAndId(token,id).orElse(null);
        if(game==null){
            return gameUtils.expelPlayer();
        }
        if(game!=null && game.getWinner()!=null){
            res = new ModelAndView("redirect:/localGame/res/" + game.getId() + "?token=" + token);
        }else {
            Image logo = new Image();
            res.addObject("logo", logo);
            res.addObject("game",game);
            String imageSelected;
            if(game.getActualPlayer().equals(game.getPlayer1Name())){
                imageSelected = "/images/"+game.getPlayer1Image().getImageType()+"/"+game.getPlayer1Image().getResourceName()+".png";
                res.addObject("imageUrl", imageSelected);
            }else{
                imageSelected = "/images/"+game.getPlayer2Image().getImageType()+"/"+game.getPlayer2Image().getResourceName()+".png";
                res.addObject("imageUrl", imageSelected);
            }
            Position p = new Position(game.getX(),game.getY());
            p = gameUtils.randomImagePortion(imageSelected, p);
            while(!gameUtils.checkImageHasMoreThan1Color(imageSelected, p)){
                p = gameUtils.randomImagePortion(imageSelected, p);
            }
            game.setX(p.getX());
            game.setY(p.getY());
            String imageStyle = gameUtils.generateImageStyle(imageSelected, p);
            res.addObject("imageStyle", imageStyle);
        }
        return res;
    }

    @PostMapping("/play/{id}")
    public ModelAndView checkGame(@ModelAttribute("logo") Image logo,@PathVariable("id") Integer id,@RequestParam(required = false) String token){
        ModelAndView res = new ModelAndView("redirect:/welcome");
        if(token == null){
            return gameUtils.expelPlayer();
        }
        LocalGame game = this.localGameService.getLocalGameByTokenAndId(token,id).orElse(null);
        if(game == null){
            return gameUtils.expelPlayer();
        }else{
            if(game.getActualPlayer().equals(game.getPlayer1Name())){
                game.setPlayer1Shifts(game.getPlayer1Shifts()+1);
                if(logo.getName().equals(game.getPlayer1Image().getName())){
                    game.setPlayer1FInish(LocalDateTime.now());
                    game.setActualPlayer(game.getPlayer2Name());
                    game.setPlayer2Start(LocalDateTime.now());
                }
                res = new ModelAndView("redirect:/localGame/play/" + game.getId() + "?token=" + game.getToken());
            }else{
                game.setPlayer2Shifts(game.getPlayer2Shifts()+1);
                if(logo.getName().equals(game.getPlayer2Image().getName())){
                    game.setPlayer2Finish(LocalDateTime.now());
                    res = new ModelAndView("redirect:/localGame/res/"+game.getId());
                    game.setActualPlayer(null);
                    game.setWinner(game.checkWinner(game.getPlayer1Name(), game.getPlayer2Name()));
                    this.localGameService.save(game);
                }
                res = new ModelAndView("redirect:/localGame/res/" + game.getId() + "?token=" + game.getToken());
            }
        }
        this.localGameService.save(game);
        return res;
    }

    @GetMapping("/res/{id}")
    public ModelAndView localGameRes(@PathVariable("id") Integer id, @RequestParam(required = false) String token){
        if(token == null){
            return gameUtils.expelPlayer();
        }
        LocalGame game = this.localGameService.getLocalGameByTokenAndId(token,id).orElse(null);
        ModelAndView res = new ModelAndView(RES_LOCAL_GAME);
        if(game==null){
            return gameUtils.expelPlayer();
        }else{
            if(game!=null && game.getWinner()!=null){
                res = gameResult(game, res);
            }else {
                res = new ModelAndView("redirect:/localGame/res/" + game.getId() + "?token=" + game.getToken());
            }
        }
        return res;
    }

    public ModelAndView gameResult(LocalGame game,ModelAndView res){
        if(game.getWinner().equals("draw")){
            res.addObject("winnerMsg", "Vaya, habeis empatado, los dos habeis usado los mismos intetos y habeis tardado el mismo tiempo");
        }else{
            res.addObject("winnerMsg", "El ganador ha sido " + game.getWinner());
        }
        Duration player1Time = Duration.between(game.getPlayer1Start(),game.getPlayer1FInish());
        Duration player2Time = Duration.between(game.getPlayer2Start(),game.getPlayer2Finish());
        res.addObject("shiftsP1",game.getPlayer1Name() +  " ha necesitado " + game.getPlayer1Shifts() + " intentos y ha tardado " + player1Time.getSeconds()+ " segundos" );
        res.addObject("shiftsP2",game.getPlayer2Name() +  " ha necesitado " + game.getPlayer2Shifts() + " intentos y ha tardado " + player2Time.getSeconds()+ " segundos" );
        String imageSelected = "/images/"+game.getPlayer1Image().getImageType()+"/"+game.getPlayer1Image().getResourceName()+".png";
        res.addObject("imageUrl1", imageSelected);
        imageSelected = "/images/"+game.getPlayer2Image().getImageType()+"/"+game.getPlayer2Image().getResourceName()+".png";
        res.addObject("imageUrl2", imageSelected);
        return res;
    }

    
}
