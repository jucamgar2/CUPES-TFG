package TFG.CUPES.Game;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import TFG.CUPES.Player.Player;
import TFG.CUPES.Player.PlayerService;

@Controller
@RequestMapping("/onlineGame")
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

    @Autowired
    public OnlineGameController(OnlineGameService onlineGameService,PlayerService playerService,ImageService imageService){
        this.onlineGameService = onlineGameService;
        this.playerService = playerService;
        this.imageService = imageService;
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
        ModelAndView res = new ModelAndView("redirect:/onlineGame/join/" + game.getId());
        return res;
    }

    @GetMapping("/join")
    public ModelAndView join(Principal principal,HttpServletResponse response){
        response.addHeader("Refresh", "4");
        ModelAndView res = new ModelAndView(JOIN_GAME);
        List<OnlineGame> games = this.onlineGameService.getNotStartedGames();
        res.addObject("games", games);
        return res;
    }

    @GetMapping("/joinning/{id}")
    public ModelAndView joinning(@PathVariable("id") Integer id,Principal principal){
        ModelAndView res = new ModelAndView("redirect:/onlineGame/join/" + id);
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
        response.addHeader("Refresh", "1");
        ModelAndView res = new ModelAndView(LOBBY);
        OnlineGame game = this.onlineGameService.getOnlineGameByid(id).orElse(null);
        if (game == null || (game.getPlayer2() != null && (!principal.getName().equals(game.getPlayer2().getUsername()) && !principal.getName().equals(game.getPlayer1().getUsername())))) {
            return gameUtils.expelPlayer();
        }
        if(game.getGameStart()!=null &&game.getGameStart()){
            res = new ModelAndView("redirect:/onlineGame/play/" + game.getId());
        }
        res.addObject("game", game);
        return res;
    }

    @GetMapping("/start/{id}")
    public ModelAndView lobby(@PathVariable("id") Integer id, Principal principal){
        ModelAndView res = new ModelAndView("redirect:/onlineGame/join/" + id);
        OnlineGame game = this.onlineGameService.getOnlineGameByid(id).orElse(null);
        Player player = this.playerService.findByUsername(principal.getName());
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
        if(game.getPlayer1Leaves() || game.getPlayer2Leaves()){
            return new ModelAndView("redirect:/onlineGame/finish/" + id);
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
                Position p = new Position(game.getPlayer1X(),game.getPlayer1Y());
                p = gameUtils.randomImagePortion(imageSelected, p);
                while(!gameUtils.checkImageHasMoreThan1Color(imageSelected, p)){
                    p = gameUtils.randomImagePortion(imageSelected, p);
                }
                game.setPlayer1X(p.getX());
                game.setPlayer1Y(p.getY());
                this.onlineGameService.save(game);
                String imageStyle = gameUtils.generateImageStyle(imageSelected, p);
                res.addObject("imageStyle", imageStyle);
            }else if(principal.getName().equals(game.getPlayer2().getUsername())){
                Position p = new Position(game.getPlayer2X(),game.getPlayer2Y());
                p = gameUtils.randomImagePortion(imageSelected, p);
                while(!gameUtils.checkImageHasMoreThan1Color(imageSelected, p)){
                    p = gameUtils.randomImagePortion(imageSelected, p);
                }
                game.setPlayer2X(p.getX());
                game.setPlayer2Y(p.getY());
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
        ModelAndView res = new ModelAndView("redirect:/onlineGame/play/" + id);
        if(game.getPlayer1Leaves() || game.getPlayer2Leaves()){
            return new ModelAndView("redirect:/onlineGame/finish/" + id);
        }
        if(game !=null){
            if(principal.getName().equals(game.getPlayer1().getUsername())){
                checkSucces(game, principal, logo);
                if(game.getPlayer1Succes() == 3){
                    game.setPlayer1FInish(LocalDateTime.now());
                    this.onlineGameService.save(game);
                    return new ModelAndView("redirect:/onlineGame/stand/" + id);
                }
            }else if(principal.getName().equals(game.getPlayer2().getUsername())){
                checkSucces(game, principal, logo);
                if(game.getPlayer2Succes() == 3){
                    game.setPlayer2Finish(LocalDateTime.now());
                    this.onlineGameService.save(game);
                    return new ModelAndView("redirect:/onlineGame/stand/" + id);
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
        response.addHeader("Refresh", "1");
        ModelAndView res = new ModelAndView(STAND_GAME);
        OnlineGame game = this.onlineGameService.getOnlineGameByid(id).orElse(null);
        if(game.getPlayer1Leaves()|| game.getPlayer2Leaves()){
            return new ModelAndView("redirect:/onlineGame/finish/" + id);
        }
        if(game!=null){
            res.addObject("game", game);
            if(game.getPlayer1FInish()!=null && game.getPlayer2Finish()!=null){
                game.setWinner(game.checkWinner(game.getPlayer1().getUsername(), game.getPlayer2().getUsername()));
                this.onlineGameService.save(game);
                res = new ModelAndView("redirect:/onlineGame/finish/" + id);
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
            if(principal.getName().equals(game.getPlayer1().getUsername()) || principal.getName().equals(game.getPlayer2().getUsername())){
                res.addObject("game", game);
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
                    game.setPlayer2(null);
                }else{
                    game.setWinner(game.getPlayer1().getUsername());
                    game.setPlayer2Leaves(true);
                    game.setWinner(game.getPlayer1().getUsername());
                    this.onlineGameService.save(game);
                    res.addObject("message","Has abandonado la partida y por tanto has perdido");
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
        game.setPlayer1Image2(imageService.getRandomLogo());
        game.setPlayer2Image2(imageService.getRandomLogo());
        game.setPlayer1Image3(imageService.getRandomLogo());
        game.setPlayer2Image3(imageService.getRandomLogo());
        game.setPlayer1Shifts(0);
        game.setPlayer2Shifts(0);
        game.setPlayer1Succes(0);
        game.setPlayer2Succes(0);
        this.onlineGameService.save(game);
    }

    public String setPlayerImage(OnlineGame game,ModelAndView res,Principal principal){
        String imageSelected = "";
        if(game.getPlayer1().getUsername().equals(principal.getName())){
            if(game.getPlayer1Succes() == 0){
                imageSelected = "/images/"+game.getPlayer1Image1().getImageType()+"/"+game.getPlayer1Image1().getResourceName()+".png";
            }else if(game.getPlayer1Succes() ==1){
                imageSelected = "/images/"+game.getPlayer1Image2().getImageType()+"/"+game.getPlayer1Image2().getResourceName()+".png";
            }else{
                imageSelected = "/images/"+game.getPlayer1Image3().getImageType()+"/"+game.getPlayer1Image3().getResourceName()+".png";
            }
        }else{
            if(game.getPlayer2Succes() == 0){
                imageSelected = "/images/"+game.getPlayer2Image1().getImageType()+"/"+game.getPlayer2Image1().getResourceName()+".png";
            }else if(game.getPlayer2Succes() ==1){
                imageSelected = "/images/"+game.getPlayer2Image2().getImageType()+"/"+game.getPlayer2Image2().getResourceName()+".png";
            }else{
                imageSelected = "/images/"+game.getPlayer2Image3().getImageType()+"/"+game.getPlayer2Image3().getResourceName()+".png";
            }
        }
        return imageSelected;   
    }

    public void checkSucces(OnlineGame game,Principal principal,Image logo){
        if(principal.getName().equals(game.getPlayer1().getUsername())){
            game.setPlayer1Shifts(game.getPlayer1Shifts()+1);
            if(game.getPlayer1Succes() == 0){
                if(game.getPlayer1Image1().getName().equals(logo.getName())){
                    game.setPlayer1Succes(game.getPlayer1Succes()+1);
                }
            }else if(game.getPlayer1Succes() == 1){
                if(game.getPlayer1Image2().getName().equals(logo.getName())){
                    game.setPlayer1Succes(game.getPlayer1Succes()+1);
                }
            }else{
                if(game.getPlayer1Image3().getName().equals(logo.getName())){
                    game.setPlayer1Succes(game.getPlayer1Succes()+1);
                }
            }
        }else if(principal.getName().equals(game.getPlayer2().getUsername())){
            game.setPlayer2Shifts(game.getPlayer2Shifts()+1);
            if(game.getPlayer2Succes() == 0){
                if(game.getPlayer2Image1().getName().equals(logo.getName())){
                    game.setPlayer2Succes(game.getPlayer2Succes()+1);
                }
            }else if(game.getPlayer2Succes() == 1){
                if(game.getPlayer2Image2().getName().equals(logo.getName())){
                    game.setPlayer2Succes(game.getPlayer2Succes()+1);
                }
            }else{
                if(game.getPlayer2Image3().getName().equals(logo.getName())){
                    game.setPlayer2Succes(game.getPlayer2Succes()+1);
                }
            }
        }
        this.onlineGameService.save(game);
    }
    
}

