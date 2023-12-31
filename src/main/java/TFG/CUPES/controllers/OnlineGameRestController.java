package TFG.CUPES.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import TFG.CUPES.entities.OnlineGame;
import TFG.CUPES.services.OnlineGameService;

@Controller
public class OnlineGameRestController {

    OnlineGameService onlineGameService;

    @Autowired
    public OnlineGameRestController(OnlineGameService onlineGameService) {
        this.onlineGameService = onlineGameService;
    }

     @GetMapping("/stand/{gameId}")
    public ResponseEntity<Boolean> checkGameStatus(@PathVariable("gameId") Integer gameId) {
        OnlineGame onlineGame = onlineGameService.getOnlineGameByid(gameId).orElse(null);
        Boolean bothPlayersFinished=false;
        bothPlayersFinished =(onlineGame.getPlayer1FInish()!=null && onlineGame.getPlayer2Finish()!=null) || onlineGame.getPlayer1Leaves() || onlineGame.getPlayer2Leaves();
        return ResponseEntity.ok(bothPlayersFinished);
    }

    @GetMapping("/start/{gameId}")
    public ResponseEntity<Boolean> checkLobbyStatus(@PathVariable("gameId") Integer gameId) {
        OnlineGame onlineGame = onlineGameService.getOnlineGameByid(gameId).orElse(null);
        Boolean bothPlayersJoined=false;
        bothPlayersJoined =onlineGame.getPlayer1()!=null && onlineGame.getPlayer2()!=null && onlineGame.getPlayer1IsReady() && onlineGame.getPlayer2IsReady();
        return ResponseEntity.ok(bothPlayersJoined);
    }

    @GetMapping("/lobby")
    public ResponseEntity<Boolean> checkLobbyStatus() {
        List<OnlineGame> games = this.onlineGameService.getNotStartedGames();
        Boolean gameJoin=false;
        gameJoin=!games.isEmpty();
        return ResponseEntity.ok(gameJoin);
    }
    
}
