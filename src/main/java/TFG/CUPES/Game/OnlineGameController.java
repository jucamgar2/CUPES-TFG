package TFG.CUPES.Game;

import java.io.IOException;
import java.security.Principal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import TFG.CUPES.Player.Player;
import TFG.CUPES.Player.PlayerService;

@Controller
@RequestMapping("/game/onlineGame")
public class OnlineGameController {

    private final static String JOIN_GAME = "/game/join";
    private final static String LOBBY = "/game/lobby"; 
    private final static String PLAY_GAME = "/game/playOnlineGame";
    private final static String STAND_GAME = "/game/stand";
    private final static String FINISH_GAME = "/game/finish";
   
    GameUtils gameUtils = new GameUtils();
    
    private OnlineGameService onlineGameService;
    private ImageService imageService;
    private PlayerService playerService;
    private PositionService positionService;

    @Autowired
    public OnlineGameController(OnlineGameService onlineGameService,PlayerService playerService,ImageService imageService,PositionService positionService){
        this.onlineGameService = onlineGameService;
        this.playerService = playerService;
        this.imageService = imageService;
        this.positionService = positionService;
    }

    @GetMapping("/new")
    public ModelAndView select(Principal principal){
        OnlineGame game = new OnlineGame();
        Player player1 = this.playerService.findByUsername(principal.getName());
        game.setPlayer1(player1);
        game.setPlayer1IsReady(false);
        game.setPlayer2IsReady(false);
        game.setGameStart(false);
        this.onlineGameService.save(game);
        ModelAndView res = new ModelAndView("redirect:/game/onlineGame/join/" + game.getId());
        return res;
    }

    @GetMapping("/join")
    public ModelAndView join(Principal principal,HttpServletResponse response){
        response.addHeader("Refresh", "4");
        ModelAndView res = new ModelAndView(JOIN_GAME);
        List<OnlineGame> games = this.onlineGameService.getNotStartedGames();
        if(!games.isEmpty()){
            Random random = new Random();
            OnlineGame game = games.get(random.nextInt(games.size()));
            return new ModelAndView("redirect:/game/onlineGame/joinning/" + game.getId());
        }
        return res;
    }

    @GetMapping("/joinning/{id}")
    public ModelAndView joinning(@PathVariable("id") Integer id,Principal principal){
        ModelAndView res = new ModelAndView("redirect:/game/onlineGame/join/" + id);
        OnlineGame game = this.onlineGameService.getOnlineGameByid(id).orElse(null);
        if(game == null || game.getPlayer2() != null){
           return gameUtils.expelPlayer();
        }
        Player player2 = this.playerService.findByUsername(principal.getName());
        game.setPlayer2(player2);
        this.onlineGameService.save(game);
        return res;
    }

    @GetMapping("/join/{id}")
    public ModelAndView lobby(@PathVariable("id") Integer id,Principal principal,HttpServletResponse response){
        ModelAndView res = new ModelAndView(LOBBY);
        response.addHeader("Refresh", "4");
        OnlineGame game = this.onlineGameService.getOnlineGameByid(id).orElse(null);
        if (game == null || (game.getPlayer2() != null && (!principal.getName().equals(game.getPlayer2().getUsername()) && !principal.getName().equals(game.getPlayer1().getUsername())))) {
            return gameUtils.expelPlayer();
        }
        if(game.getGameStart()!=null &&game.getGameStart()){
            res = new ModelAndView("redirect:/game/onlineGame/play/" + game.getId());
        }
        res.addObject("game", game);
        return res;
    }

    @GetMapping("/start/{id}")
    public ModelAndView lobby(@PathVariable("id") Integer id, Principal principal){
        ModelAndView res = new ModelAndView("redirect:/game/onlineGame/join/" + id);
        OnlineGame game = this.onlineGameService.getOnlineGameByid(id).orElse(null);
        Player player = this.playerService.findByUsername(principal.getName());
        if(game ==null){
            return gameUtils.expelPlayer();
        }
        if(player.equals(game.getPlayer1())){
            game.setPlayer1IsReady(true);
        }else if(player.equals(game.getPlayer2())){
            game.setPlayer2IsReady(true);
        }else{
            return gameUtils.expelPlayer();
        }
        if(game.getPlayer1IsReady() && game.getPlayer2IsReady()){
            game.setGameStart(true);
            game.setPlayer1Leaves(false);
            game.setPlayer2Leaves(false);
        }
        this.onlineGameService.save(game);
        return res;
    }

    @GetMapping("/play/{id}")
    public ModelAndView playOnlineGame(@PathVariable("id") Integer id,Principal principal) throws IOException{
        ModelAndView res = new ModelAndView(PLAY_GAME);
        res.addObject("principal", principal);
        OnlineGame game = this.onlineGameService.getOnlineGameByid(id).orElse(null);
        List<Position> positions = this.positionService.findAll();
        if(game.getPlayer1Leaves() || game.getPlayer2Leaves()){
            return new ModelAndView("redirect:/game/onlineGame/finish/" + id);
        }
        if(game !=null){
            Image logo = new Image();
            res.addObject("logo", logo);
            res.addObject("game",game);
            String imageSelected;
            if(game.getPlayer1Start()==null || game.getPlayer2Start() == null){
                preparateOnlineGame(game);
            }
            imageSelected = setPlayerImage(game, res, principal);
            res.addObject("imageUrl", imageSelected);
            if(principal.getName().equals(game.getPlayer1().getUsername())){
                Position p;
                if(game.getPlayer1X()==null || game.getPlayer1Y()==null){
                    p = new Position(0,0);
                }else{
                    p = new Position(game.getPlayer1X(),game.getPlayer1Y());
                }
                if(game.getPlayer1Redt() || game.getPlayer1Positions().isEmpty()){
                    game.setPlayer1Redt(false);
                    p = gameUtils.randomImagePortion(imageSelected, game.getPlayer1Positions(), positions);
                    while(!gameUtils.checkImageHasMoreThan1Color(imageSelected, p)){
                        p = gameUtils.randomImagePortion(imageSelected, game.getPlayer1Positions(), positions);
                    }
                    game.setPlayer1X(p.getX());
                    game.setPlayer1Y(p.getY());
                    game.getPlayer1Positions().add(p);
                    this.onlineGameService.save(game);
                }
                String fullImageStyle = gameUtils.generateImageStyle(positions, game.getPlayer1Positions());
                res.addObject("fullImageStyle", fullImageStyle);
                String imageStyle = gameUtils.generateImageStyle(imageSelected, p);
                res.addObject("imageStyle", imageStyle);
            }else if(principal.getName().equals(game.getPlayer2().getUsername())){
                Position p;
                if(game.getPlayer2X()==null || game.getPlayer2Y()==null){
                    p = new Position(0,0);
                }else{
                    p = new Position(game.getPlayer2X(),game.getPlayer2Y());
                }
                if(game.getPlayer2Redt()|| game.getPlayer2Positions().isEmpty()){
                    p = gameUtils.randomImagePortion(imageSelected, game.getPlayer2Positions(), positions);
                    while(!gameUtils.checkImageHasMoreThan1Color(imageSelected, p)){
                        p = gameUtils.randomImagePortion(imageSelected, game.getPlayer2Positions(), positions);
                    }
                    game.setPlayer2X(p.getX());
                    game.setPlayer2Y(p.getY());
                    game.getPlayer2Positions().add(p);
                    game.setPlayer2Redt(false);
                    this.onlineGameService.save(game);
                }
                String fullImageStyle = gameUtils.generateImageStyle(positions, game.getPlayer2Positions());
                res.addObject("fullImageStyle", fullImageStyle);
                String imageStyle = gameUtils.generateImageStyle(imageSelected, p);
                res.addObject("imageStyle", imageStyle);
                this.onlineGameService.save(game);
            }else{
                return gameUtils.expelPlayer();
            }
        }else{
            return gameUtils.expelPlayer();
        }
        return res;
    }

    @PostMapping("/play/{id}")
    public ModelAndView playGame(@ModelAttribute("logo") Image logo,@PathVariable("id") Integer id,Principal principal){
        OnlineGame game = this.onlineGameService.getOnlineGameByid(id).orElse(null);
        ModelAndView res = new ModelAndView("redirect:/game/onlineGame/play/" + id );
        if(game.getPlayer1Leaves() || game.getPlayer2Leaves()){
            return new ModelAndView("redirect:/game/onlineGame/finish/" + id);
        }
        if(game !=null){
            if(principal.getName().equals(game.getPlayer1().getUsername())){
                checkSucces(game, principal, logo);
                if(game.getPlayer1Succes() == 3 || game.getPlayer1FInish()!=null){
                    game.setPlayer1FInish(LocalDateTime.now());
                    this.onlineGameService.save(game);
                    return new ModelAndView("redirect:/game/onlineGame/stand/" + id);
                }
            }else if(principal.getName().equals(game.getPlayer2().getUsername())){
                checkSucces(game, principal, logo);
                if(game.getPlayer2Succes() == 3 || game.getPlayer2Finish()!=null){
                    game.setPlayer2Finish(LocalDateTime.now());
                    this.onlineGameService.save(game);
                    return new ModelAndView("redirect:/game/onlineGame/stand/" + id);
                }
            }else{
                return gameUtils.expelPlayer();
            }
        }else{
            return gameUtils.expelPlayer();
        }
        return res;
    }

    @GetMapping("/stand/{id}")
    public ModelAndView stand(@PathVariable("id") Integer id, Principal principal, HttpServletResponse response){
        ModelAndView res = new ModelAndView(STAND_GAME);
        OnlineGame game = this.onlineGameService.getOnlineGameByid(id).orElse(null);
        if(game.getPlayer1Leaves()|| game.getPlayer2Leaves()){
            return new ModelAndView("redirect:/game/onlineGame/finish/" + id);
        }
        if(game!=null){
            res.addObject("game", game);
            if(game.getPlayer1FInish()!=null && game.getPlayer2Finish()!=null){
                checkPlayersCanWin(game);
                game.setWinner(game.checkWinner(game.getPlayer1().getUsername(), game.getPlayer2().getUsername()));
                this.onlineGameService.save(game);
                res = new ModelAndView("redirect:/game/onlineGame/finish/" + id);
            }
        }else{
            return gameUtils.expelPlayer();
        }
        return res;
    }

    @GetMapping("/finish/{id}")
    public ModelAndView gameResult(@PathVariable("id")Integer id, Principal principal){
        ModelAndView res = new ModelAndView(FINISH_GAME);
        OnlineGame game = this.onlineGameService.getOnlineGameByid(id).orElse(null);
        if(game!=null){
            if(game.getWinner()!=null && game.getPlayer1FInish()!=null && game.getPlayer2Finish()!=null){
                checkPlayersCanWin(game);
                game.setWinner(game.checkWinner(game.getPlayer1().getUsername(),game.getPlayer2().getUsername()));
                this.onlineGameService.save(game);
            }
            res.addObject("game", game);
            res.addObject("imageUrl1","/images/"+game.getPlayer1Image1().getImageType()+"/"+game.getPlayer1Image1().getResourceName()+".jpg");
            res.addObject("imageUrl2","/images/"+game.getPlayer1Image2().getImageType()+"/"+game.getPlayer1Image2().getResourceName()+".jpg");
            res.addObject("imageUrl3","/images/"+game.getPlayer1Image3().getImageType()+"/"+game.getPlayer1Image3().getResourceName()+".jpg");
            res.addObject("imageUrl4","/images/"+game.getPlayer2Image1().getImageType()+"/"+game.getPlayer2Image1().getResourceName()+".jpg");
            res.addObject("imageUrl5","/images/"+game.getPlayer2Image2().getImageType()+"/"+game.getPlayer2Image2().getResourceName()+".jpg");
            res.addObject("imageUrl6","/images/"+game.getPlayer2Image3().getImageType()+"/"+game.getPlayer2Image3().getResourceName()+".jpg");
            if(game.getPlayer1Leaves() || game.getPlayer2Leaves()){
                res.addObject("leavemsg", "El ganador ha sido " + game.getWinner() + " ya que el otro jugador ha abandonado la partida");
                return res;
            }

            if(principal.getName().equals(game.getPlayer1().getUsername()) || principal.getName().equals(game.getPlayer2().getUsername())){
                Duration player1Time = Duration.between(game.getPlayer1Start(), game.getPlayer1FInish());
                Duration player2Time = Duration.between(game.getPlayer2Start(), game.getPlayer2Finish());
                res.addObject("player1Time", player1Time.getSeconds());
                res.addObject("player2Time", player2Time.getSeconds());
                
            }else{
                return gameUtils.expelPlayer();
            }
        }else{
            return gameUtils.expelPlayer();
        }
        return res;
    }

    @GetMapping("/leave/{id}")
    public ModelAndView leave(@PathVariable("id") Integer id,Principal principal){
        ModelAndView res = new ModelAndView("redirect:/");
        OnlineGame game = this.onlineGameService.getOnlineGameByid(id).orElse(null);
        if(game != null){
            if(principal.getName().equals(game.getPlayer1().getUsername())){
                if(!game.getGameStart()){
                    res.addObject("message","Has abandonado la partida y esta ha sido borrada ya que no había empezado");
                    this.onlineGameService.delete(game);
                }else{
                    game.setWinner(game.getPlayer2().getUsername());
                    game.setPlayer1Leaves(true);
                    game.setWinner(game.getPlayer2().getUsername());
                    res.addObject("message","Has abandonado la partida y por tanto has perdido");
                    this.onlineGameService.save(game);
                }
            }else if(principal.getName().equals(game.getPlayer2().getUsername())){
                if(!game.getGameStart()){
                    res.addObject("message","Has abandonado la partida antes de que empezara, por lo que ahora queda un hueco");
                    game.removePlayer2();
                    game.setPlayer2IsReady(false);
                    this.onlineGameService.save(game);
                }else{
                    game.setWinner(game.getPlayer1().getUsername());
                    game.setPlayer2Leaves(true);
                    game.setWinner(game.getPlayer1().getUsername());
                    this.onlineGameService.save(game);
                    res.addObject("message","Has abandonado la partida y por tanto has perdido");
                    this.onlineGameService.save(game);
                }
            }
        }
        return res;
    }

    public void preparateOnlineGame(OnlineGame game){
        LocalDateTime now = LocalDateTime.now();
        game.setPlayer1Start(now);
        game.setPlayer2Start(now);
        game.setPlayer1Image1(imageService.getRandomLogo());
        game.setPlayer2Image1(imageService.getRandomLogo());
        Image player1Image2 = imageService.getRandomLogo();
        while(player1Image2.getName().equals(game.getPlayer1Image1().getName())){
            player1Image2 = imageService.getRandomLogo();
        }
        game.setPlayer1Image2(player1Image2);
        Image player2Image2 = imageService.getRandomLogo();
        while(player2Image2.getName().equals(game.getPlayer2Image1().getName())){
            player2Image2 = imageService.getRandomLogo();
        }
        game.setPlayer2Image2(player2Image2);
        Image player1Image3 = imageService.getRandomLogo();
        while(player1Image3.getName().equals(game.getPlayer1Image1().getName()) || player1Image3.getName().equals(game.getPlayer1Image2().getName())){
            player1Image3 = imageService.getRandomLogo();
        }
        game.setPlayer1Image3(player1Image3);
        Image player2Image3 = imageService.getRandomLogo();
        while(player2Image3.getName().equals(game.getPlayer2Image1().getName()) || player2Image3.getName().equals(game.getPlayer2Image2().getName())){
            player2Image3 = imageService.getRandomLogo();
        }
        game.setPlayer2Image3(player2Image3);
        game.setPlayer1Shifts(0);
        game.setPlayer2Shifts(0);
        game.setPlayer1Succes(0);
        game.setPlayer2Succes(0);
        game.setCurrentPlayer1Image(0);
        game.setCurrentPlayer2Image(0);
        game.setPlayer1Redt(false);
        game.setPlayer2Redt(false);
        this.onlineGameService.save(game);
    }

    public String setPlayerImage(OnlineGame game,ModelAndView res,Principal principal){
        String imageSelected = "";
        if(game.getPlayer1().getUsername().equals(principal.getName())){
            if(game.getCurrentPlayer1Image() == 0){
                imageSelected = "/images/"+game.getPlayer1Image1().getImageType()+"/"+game.getPlayer1Image1().getResourceName()+".jpg";
            }else if(game.getCurrentPlayer1Image() ==1){
                imageSelected = "/images/"+game.getPlayer1Image2().getImageType()+"/"+game.getPlayer1Image2().getResourceName()+".jpg";
            }else{
                imageSelected = "/images/"+game.getPlayer1Image3().getImageType()+"/"+game.getPlayer1Image3().getResourceName()+".jpg";
            }
        }else{
            if(game.getCurrentPlayer2Image() == 0){
                imageSelected = "/images/"+game.getPlayer2Image1().getImageType()+"/"+game.getPlayer2Image1().getResourceName()+".jpg";
            }else if(game.getCurrentPlayer2Image() ==1){
                imageSelected = "/images/"+game.getPlayer2Image2().getImageType()+"/"+game.getPlayer2Image2().getResourceName()+".jpg";
            }else{
                imageSelected = "/images/"+game.getPlayer2Image3().getImageType()+"/"+game.getPlayer2Image3().getResourceName()+".jpg";
            }
        }
        return imageSelected;   
    }

    public void checkSucces(OnlineGame game,Principal principal,Image logo){
        if(principal.getName().equals(game.getPlayer1().getUsername())){
            game.setPlayer1Shifts(game.getPlayer1Shifts()+1);
            if(game.getCurrentPlayer1Image() == 0){
                if(game.getPlayer1Image1().getName().equals(logo.getName())){
                    game.setPlayer1Succes(game.getPlayer1Succes()+1);
                    game.setCurrentPlayer1Image(1);
                    game.getPlayer1Positions().clear();
                }else{
                    checkPlayersFails4Time(game, principal);
                }
            }else if(game.getCurrentPlayer1Image() == 1){
                if(game.getPlayer1Image2().getName().equals(logo.getName())){
                    game.setPlayer1Succes(game.getPlayer1Succes()+1);
                    game.setCurrentPlayer1Image(2);
                    game.getPlayer1Positions().clear();;
                }else{
                    checkPlayersFails4Time(game, principal);
                }
            }else{
                if(game.getPlayer1Image3().getName().equals(logo.getName())){
                    game.setPlayer1Succes(game.getPlayer1Succes()+1);
                }else{
                    checkPlayersFails4Time(game, principal);
                }
            }
            game.setPlayer1Redt(true);
        }else if(principal.getName().equals(game.getPlayer2().getUsername())){
            game.setPlayer2Shifts(game.getPlayer2Shifts()+1);
            if(game.getCurrentPlayer2Image() == 0){
                if(game.getPlayer2Image1().getName().equals(logo.getName())){
                    game.setPlayer2Succes(game.getPlayer2Succes()+1);
                    game.setCurrentPlayer2Image(1);
                    game.getPlayer2Positions().clear();
                }else{
                    checkPlayersFails4Time(game, principal);
                }
            }else if(game.getCurrentPlayer2Image() == 1){
                if(game.getPlayer2Image2().getName().equals(logo.getName())){
                    game.setPlayer2Succes(game.getPlayer2Succes()+1);
                    game.setCurrentPlayer2Image(2);
                    game.getPlayer2Positions().clear();;
                }else{
                    checkPlayersFails4Time(game, principal);
                }
            }else{
                if(game.getPlayer2Image3().getName().equals(logo.getName())){
                    game.setPlayer2Succes(game.getPlayer2Succes()+1);
                }else{
                    checkPlayersFails4Time(game, principal);
                }
            }
            game.setPlayer2Redt(true);
        }
        this.onlineGameService.save(game);
    }

    public void checkPlayersFails4Time(OnlineGame game,Principal principal){
        if(principal.getName().equals(game.getPlayer1().getUsername())){
            if(game.getPlayer1Positions().size()==5){
                game.setCurrentPlayer1Image(game.getCurrentPlayer1Image()+1);
                game.getPlayer1Positions().clear();
                if(game.getCurrentPlayer1Image() == 3){
                    game.setPlayer1FInish(LocalDateTime.now());
                }
            }
        }else{
            if(game.getPlayer2Positions().size()==5){
                game.setCurrentPlayer2Image(game.getCurrentPlayer2Image()+1);
                game.getPlayer2Positions().clear();
                if(game.getCurrentPlayer2Image() == 3){
                    game.setPlayer2Finish(LocalDateTime.now());
                }
            }
        } 
    }

    public void checkPlayersCanWin(OnlineGame game){
        if(game.getPlayer1Succes() > game.getPlayer2Succes()){
            game.setPlayer1CanWin(true);
            game.setPlayer2CanWin(false);
        }else if( game.getPlayer1Succes()<game.getPlayer2Succes()){
            game.setPlayer1CanWin(false);
            game.setPlayer2CanWin(true);
        }else{
            game.setPlayer1CanWin(true);
            game.setPlayer2CanWin(true);
        }
        this.onlineGameService.save(game);
    }    
}

