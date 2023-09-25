package TFG.CUPES.Game;

import java.security.Principal;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import TFG.CUPES.Player.Player;
import TFG.CUPES.Player.PlayerService;

@Controller
@RequestMapping("/onlineGame")
public class OnlineGameController {

    private final static String JOIN_GAME = "/game/join";
    private final static String LOBBY = "/game/lobby"; 
   
    GameUtils gameUtils = new GameUtils();
    
    private OnlineGameService onlineGameService;

    private PlayerService playerService;

    @Autowired
    public OnlineGameController(OnlineGameService onlineGameService,PlayerService playerService){
        this.onlineGameService = onlineGameService;
        this.playerService = playerService;
    }

    @GetMapping("/new")
    public ModelAndView select(Principal principal){
        OnlineGame game = new OnlineGame();
        Player player1 = this.playerService.findByUsername(principal.getName());
        game.setPlayer1(player1);
        this.onlineGameService.save(game);
        ModelAndView res = new ModelAndView("redirect:/onlineGame/" + game.getId());
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

    @GetMapping("/join/{id}")
    public ModelAndView lobby(@PathVariable("id") Integer id,Principal principal,HttpServletResponse response){
        response.addHeader("Refresh", "4");
        ModelAndView res = new ModelAndView(LOBBY);
        OnlineGame game = this.onlineGameService.getOnlineGameByid(id).orElse(null);
        if(game == null || game.getPlayer2() != null){
            res = new ModelAndView("redirect:/onlineGame/join");
            res.addObject("message", "La partida a la que has intentado unirte no existe o ya ha empezado");
            return res;
        }
        if(game.getGameStart()){
            res = new ModelAndView("redirect:/onlineGame/play" + game.getId());
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
            res = new ModelAndView("redirect:/");
            return res;
        }
        if(game.getPlayer1IsReady() && game.getPlayer2IsReady()){
            game.setGameStart(true);
        }
        this.onlineGameService.save(game);
        return res;
    }


    @GetMapping("/leave/{id}")
    public ModelAndView leave(@PathVariable("id") Integer id){
        ModelAndView res = new ModelAndView();
        //TODO
        return res;

    }
}

