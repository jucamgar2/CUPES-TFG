package TFG.CUPES.Game;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;
import java.util.Random;

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
@RequestMapping("/localGame")
public class LocalGameController {

    public static final String NEW_LOCAL_GAME = "game/newLocalGame";

    public static final String PLAY_LOCAL_GAME = "game/playLocalGame";

    public static final List<Integer> footballLogoPositions = List.of(0,500,1000,1500);

    public static final String RES_LOCAL_GAME ="game/resLocalGame";
    
    LocalGameService localGameService;

    ImageService imageService;

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
        this.localGameService.save(localGame);
        return "redirect:/localGame/play/"+localGame.getId();
    }

    @GetMapping("/play/{id}")
    public ModelAndView playGame(@PathVariable("id") Integer id){
        ModelAndView res = new ModelAndView(PLAY_LOCAL_GAME);
        LocalGame game = this.localGameService.getLocalGameById(id).orElse(null);
        if(game==null){
            return new ModelAndView("redirect:/welcome");
        }
        if(game!=null && game.getWinner()!=null){
            res = new ModelAndView("redirect:/localGame/res/" + game.getId());
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
            String imageStyle = generateStyle(imageSelected, game);
            res.addObject("imageStyle", imageStyle);
        }
        return res;
    }

    @PostMapping("/play/{id}")
    public ModelAndView checkGame(@ModelAttribute("logo") Image logo,@PathVariable("id") Integer id){
        ModelAndView res = new ModelAndView("redirect:/welcome");
        LocalGame game = this.localGameService.getLocalGameById(id).orElse(null);
        if(game!=null){
            if(game.getActualPlayer().equals(game.getPlayer1Name())){
                game.setPlayer1Shifts(game.getPlayer1Shifts()+1);
                if(logo.getName().equals(game.getPlayer1Image().getName())){
                    game.setPlayer1FInish(LocalDateTime.now());
                    game.setActualPlayer(game.getPlayer2Name());
                    game.setPlayer2Start(LocalDateTime.now());
                }
                res = new ModelAndView("redirect:/localGame/play/"+game.getId());
            }else{
                game.setPlayer2Shifts(game.getPlayer2Shifts()+1);
                if(logo.getName().equals(game.getPlayer2Image().getName())){
                    game.setPlayer2Finish(LocalDateTime.now());
                    res = new ModelAndView("redirect:/localGame/res/"+game.getId());
                    game.setActualPlayer(null);
                    game.setWinner(checkWinner(game));
                }
                res = new ModelAndView("redirect:/localGame/play/"+game.getId());
            }
        }
        this.localGameService.save(game);
        return res;
    }

    @GetMapping("/res/{id}")
    public ModelAndView localGameRes(@PathVariable("id") Integer id){
        LocalGame game = this.localGameService.getLocalGameById(id).orElse(null);
        ModelAndView res = new ModelAndView(RES_LOCAL_GAME);
        if(game==null){
            return new ModelAndView("redirect:/welcome");
        }else{
            if(game!=null && game.getWinner()!="null"){
                res = gameResult(game, res);
            }else {
                res = new ModelAndView("redirect:/localGame/play/"+game.getId());
            }
        }

        return res;
    }


    private String checkWinner(LocalGame game) {
        String winner;
        if(game.getPlayer1Shifts()<game.getPlayer2Shifts()){
            winner = game.getPlayer1Name();
        }else if(game.getPlayer2Shifts()<game.getPlayer1Shifts()){
            winner = game.getPlayer2Name();
        }else{
            Duration player1Time = Duration.between(game.getPlayer1Start(),game.getPlayer1FInish());
            Duration player2Time = Duration.between(game.getPlayer2Start(),game.getPlayer2Finish());
            if(player1Time.compareTo(player2Time)<0){
                winner =game.getPlayer1Name();
            }else if(player2Time.compareTo(player1Time)<0){
                 winner = game.getPlayer2Name();
            }else{
                winner = "draw";
            }
        }
        return winner;
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

    public String generateStyle(String imageSelected,LocalGame game) {
        Random rand = new Random();
        int x = 999;
        int y = 999;
        while(game.getX()==null && game.getY()==null ||(game.getX()!=x && game.getY()!=y)){
            x = footballLogoPositions.get(rand.nextInt(0, footballLogoPositions.size()));
            y = footballLogoPositions.get(rand.nextInt(0, footballLogoPositions.size()));
            game.setX(x);
            game.setY(y);
        }
        localGameService.save(game);
        return "backgroud-color: white;background-image: url('" + imageSelected + "'); width: " + 500+"px; height: " + 500 + "px; background-position: -" + game.getX() + "px -" + game.getY() + "px;";
    }
}
