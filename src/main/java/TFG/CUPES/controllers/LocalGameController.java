package TFG.CUPES.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import java.io.IOException;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import TFG.CUPES.components.GameUtils;
import TFG.CUPES.entities.Image;
import TFG.CUPES.entities.LocalGame;
import TFG.CUPES.entities.Position;
import TFG.CUPES.services.ImageService;
import TFG.CUPES.services.LocalGameService;
import TFG.CUPES.services.PositionService;

@Controller
@RequestMapping("/game/localGame")
public class LocalGameController {

    public static final String NEW_LOCAL_GAME = "game/newLocalGame";
    public static final String PLAY_LOCAL_GAME = "game/playLocalGame";
    public static final String RES_LOCAL_GAME ="game/resLocalGame";
    public static final Integer maxShifts = 4;
    GameUtils gameUtils = new GameUtils();  
    
    LocalGameService localGameService;
    ImageService imageService;
    PositionService positionService;

    @Autowired
    LocalGameController(LocalGameService localGameService, ImageService imageService, PositionService positionService){
        this.localGameService = localGameService;
        this.imageService = imageService;
        this.positionService = positionService;
    }

    @GetMapping("/new")
    public ModelAndView newLocalGame(){
        ModelAndView res = new ModelAndView(NEW_LOCAL_GAME);
        LocalGame lg = new LocalGame();
        res.addObject("localGame", lg);
        res.addObject("errors", new ArrayList<String>());
        return res;
    }

    @PostMapping("/new")
    public ModelAndView createLocalGame( LocalGame localGame,BindingResult br){
        String token = UUID.randomUUID().toString();
        ModelAndView res;
        List<String> errors = localGame.chekcLocalGame(); 
        if(!errors.isEmpty()){
            res = new ModelAndView(NEW_LOCAL_GAME);
            res.addObject("localGame", localGame);
            res.addObject("errors", errors); 
            return res;
        }
        localGame.setActualPlayer(localGame.getPlayer1Name());
        localGame.setPlayer1Image(imageService.getRandomLogo());
        localGame.setPlayer2Image(imageService.getRandomLogo());
        localGame.setPlayer1Shifts(0);
        localGame.setPlayer2Shifts(0);
        localGame.setPlayer1Start(LocalDateTime.now());
        localGame.setToken(token);
        this.localGameService.save(localGame);
        res = new ModelAndView("redirect:/game/localGame/play/"+localGame.getId() + "?token=" + token);
        return res;
    }
    
    @GetMapping("/play/{id}")
    public ModelAndView playGame(@PathVariable("id") Integer id,@RequestParam(required = false) String token) throws IOException{
        ModelAndView res = new ModelAndView(PLAY_LOCAL_GAME);
        List<Position> positions = this.positionService.findAll();
        if(token ==null){
            return gameUtils.expelPlayer();
        }
        LocalGame game = this.localGameService.getLocalGameByTokenAndId(token,id).orElse(null);
        if(game==null){
            return gameUtils.expelPlayer();
        }
        if(game!=null && game.getWinner()!=null){
            res = new ModelAndView("redirect:/game/localGame/res/" + game.getId() + "?token=" + token);
        }else {
            Image logo = new Image();
            res.addObject("logo", logo);
            res.addObject("game",game);
            String imageSelected;
            Position p = new Position(0,0);
            if(game.getActualPlayer().equals(game.getPlayer1Name())){
                imageSelected = "/images/"+game.getPlayer1Image().getImageType()+"/"+game.getPlayer1Image().getResourceName()+".jpg";
                res.addObject("imageUrl", imageSelected);
                if(game.getPlayer1Shifts()>=maxShifts){
                    return finishPlayer1(game);
                }else{
                    p = selectPlayer1Position(p, game, positions, imageSelected);
                }
                res = setupPlayer1Layout(res, game, positions, imageSelected);
            }else{
                imageSelected = "/images/"+game.getPlayer2Image().getImageType()+"/"+game.getPlayer2Image().getResourceName()+".jpg";
                res.addObject("imageUrl", imageSelected);
                if(game.getPlayer2Shifts()>=maxShifts){
                    return finishPlayer2(game);
                }else{
                    p = selectPlayer2Position(p, game, positions, imageSelected);
                }
                res = setupPlayer2Layout(game, res, positions, imageSelected);
            }
            String imageStyle = gameUtils.generateImageStyle(imageSelected, p);
            res.addObject("imageStyle", imageStyle);   
        }
        return res;
    }

    public ModelAndView finishPlayer1(LocalGame game){
        game.setPlayer1FInish(LocalDateTime.now());
        game.setPlayer1CanWin(false);
        game.setActualPlayer(game.getPlayer2Name());
        game.setPlayer2Start(LocalDateTime.now());
        this.localGameService.save(game);
        return new ModelAndView("redirect:/game/localGame/play/" + game.getId() + "?token=" + game.getToken());
    }

    public ModelAndView finishPlayer2(LocalGame game){
        game.setPlayer2Finish(LocalDateTime.now());
        game.setPlayer2CanWin(false);
        game.setActualPlayer(null);
        game.setWinner(game.checkWinner(game.getPlayer1Name(), game.getPlayer2Name()));
        this.localGameService.save(game);
        return new ModelAndView("redirect:/game/localGame/res/" + game.getId() + "?token=" + game.getToken());
    }

    public ModelAndView setupPlayer1Layout(ModelAndView res, LocalGame game, List<Position> positions, String imageSelected){
        String fullImageStyle = gameUtils.generateImageStyle(positions, game.getPlayer1Positions());
        res.addObject("fullImageStyle", fullImageStyle);
        String shiftsMessage = "Tienes 4 intentos y has gastado " + game.getPlayer1Shifts();
        res.addObject("shiftsMessage", shiftsMessage);
        if(game.getPlayer1Shifts()>0){
            List<String> errors = new ArrayList<>();
            errors.add("¡Lo siento! Fallaste en tu anterior intento");
            res.addObject("errors", errors);
        } 
        return res;
    }

    public ModelAndView setupPlayer2Layout(LocalGame game, ModelAndView res, List<Position> positions, String imageSelected) {
        if(game.getPlayer2Shifts()==0 && game.getPlayer1CanWin()){
            res.addObject("succes", true);
        }
        String shiftsMessage = "Tienes 4 intentos y has gastado " + game.getPlayer2Shifts();
        res.addObject("shiftsMessage", shiftsMessage);
        String fullImageStyle = gameUtils.generateImageStyle(positions, game.getPlayer2Positions());
        res.addObject("fullImageStyle", fullImageStyle);
        if(game.getPlayer2Shifts()>0){
            List<String> errors = new ArrayList<>();
            errors.add("¡Lo siento! Fallaste en tu anterior intento");
            res.addObject("errors", errors);
        }
        return res;
    }

    public Position selectPlayer1Position(Position p, LocalGame game, List<Position> positions, String imageSelected) throws IOException{
        if(game.getX()==null || game.getY()==null){
            p = new Position(0,0);
        }else{
            p = new Position(game.getX(),game.getY());
        }
        if(game.getPlayer1Positions().size() == game.getPlayer1Shifts()){
            p = gameUtils.randomImagePortion( game.getPlayer1Positions(), positions);
            while(!gameUtils.checkImageHasMoreThan1Color(imageSelected, p)){
                p = gameUtils.randomImagePortion( game.getPlayer1Positions(), positions);
            }
            game.setX(p.getX());
            game.setY(p.getY());
            game.getPlayer1Positions().add(p);
            this.localGameService.save(game);
        }
        return p;
    }
    
    public Position selectPlayer2Position(Position p, LocalGame game, List<Position> positions, String imageSelected) throws IOException{
        if(game.getX()==null || game.getY()==null){
            p = new Position(0,0);
        }else{
            p = new Position(game.getX(),game.getY());
        }
        if(game.getPlayer2Positions().size() == game.getPlayer2Shifts()){
            p = gameUtils.randomImagePortion( game.getPlayer2Positions(), positions);
            while(!gameUtils.checkImageHasMoreThan1Color(imageSelected, p)){
                p = gameUtils.randomImagePortion( game.getPlayer2Positions(), positions);
            }
            game.setX(p.getX());
            game.setY(p.getY());
            game.getPlayer2Positions().add(p);
            this.localGameService.save(game);
        }
        return p;
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
                res = checkPlayer1Success(game,res,logo);
            }else{
                res = checkPlayer2Success(game, res, logo);   
            }
        }
        this.localGameService.save(game);
        return res;
    }

    public ModelAndView checkPlayer1Success(LocalGame game, ModelAndView res, Image logo){
        game.setPlayer1Shifts(game.getPlayer1Shifts()+1);
        if(logo.getName().equals(game.getPlayer1Image().getName())){
            game.setPlayer1FInish(LocalDateTime.now());
            game.setActualPlayer(game.getPlayer2Name());
            game.setPlayer1CanWin(true);
            game.setPlayer2Start(LocalDateTime.now());
            this.localGameService.save(game);
        }
        res = new ModelAndView("redirect:/game/localGame/play/" + game.getId() + "?token=" + game.getToken());
        return res;
    }

    public ModelAndView checkPlayer2Success(LocalGame game, ModelAndView res, Image logo) {
        game.setPlayer2Shifts(game.getPlayer2Shifts()+1);
        if(logo.getName().equals(game.getPlayer2Image().getName())){
            game.setPlayer2Finish(LocalDateTime.now());
            res = new ModelAndView("redirect:/game/localGame/res/"+game.getId());
            game.setActualPlayer(null);
            game.setPlayer2CanWin(true);
            game.setWinner(game.checkWinner(game.getPlayer1Name(), game.getPlayer2Name()));
            this.localGameService.save(game);
            }
        res = new ModelAndView("redirect:/game/localGame/res/" + game.getId() + "?token=" + game.getToken());
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
                res = new ModelAndView("redirect:/game/localGame/play/" + game.getId() + "?token=" + game.getToken());
            }
        }
        return res;
    }

    public ModelAndView gameResult(LocalGame game,ModelAndView res){
        if(game.getWinner().equals("draw")||game.getWinner().equals("Empate")){
            res.addObject("winnerMsg", "Vaya, habeis empatado, los dos habeis usado los mismos intetos y habeis tardado el mismo tiempo");
        }else{
            res.addObject("winnerMsg", "El ganador ha sido " + game.getWinner());
        }
        Duration player1Time = Duration.between(game.getPlayer1Start(),game.getPlayer1FInish());
        Duration player2Time = Duration.between(game.getPlayer2Start(),game.getPlayer2Finish());
        if(game.getPlayer1CanWin()){
            res.addObject("shiftsP1",game.getPlayer1Name() +  " ha acertado y ha necesitado " + game.getPlayer1Shifts() + " intentos y ha tardado " + player1Time.getSeconds()+ " segundos" );
        }else{
            res.addObject("shiftsP1",game.getPlayer1Name() +  " no ha acertado y ha tardado " + player1Time.getSeconds()+ " segundos" );
        }

        if(game.getPlayer2CanWin()){
            res.addObject("shiftsP2",game.getPlayer2Name() +  " ha acertado y ha necesitado " + game.getPlayer2Shifts() + " intentos y ha tardado " + player2Time.getSeconds()+ " segundos" );
        }else{
            res.addObject("shiftsP2",game.getPlayer2Name() +  " no ha acertado y ha tardado " + player2Time.getSeconds()+ " segundos" );
        }
        String imageSelected = "/images/"+game.getPlayer1Image().getImageType()+"/"+game.getPlayer1Image().getResourceName()+".jpg";
        res.addObject("imageUrl1", imageSelected);
        imageSelected = "/images/"+game.getPlayer2Image().getImageType()+"/"+game.getPlayer2Image().getResourceName()+".jpg";
        res.addObject("imageUrl2", imageSelected);
        res.addObject("game", game);
        return res;
    }

    
}
