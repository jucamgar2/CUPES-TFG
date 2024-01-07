package TFG.CUPES.OnlineGameTest;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.stereotype.Service;

import TFG.CUPES.entities.OnlineGame;
import TFG.CUPES.entities.Player;
import TFG.CUPES.services.OnlineGameService;
import TFG.CUPES.services.PlayerService;

@DataJpaTest(includeFilters = @Filter(Service.class))
public class OnlineGameServiceTest {

    @Autowired
    private OnlineGameService onlineGameService;

    @Autowired
    private PlayerService playerService;

    Player p = new Player("María","1111",true,LocalDate.of(2015,1,1),"maria@maria.com","María");
    Player p2 = new Player("María2","1111",true,LocalDate.of(2015,1,1),"maria2@maria2.com","María2");
    Player p3 = new Player("María3","1111",true,LocalDate.of(2015,1,1),"maria3@gmail.com","María3");
    OnlineGame og = new OnlineGame(1213,p,p2,null,null,null,null,null,null,0,0,0,0,true,true,true,false,false,3,3,3,3,List.of(),List.of(),true,true);
    OnlineGame og2 = new OnlineGame(1214,p,p2,null,null,null,null,null,null,0,0,0,0,true,true,true,false,false,3,3,3,3,List.of(),List.of(),true,true);
    OnlineGame og3 = new OnlineGame(1215,p2,p3,null,null,null,null,null,null,0,0,0,0,true,true,true,false,false,3,3,3,3,List.of(),List.of(),true,true);
    OnlineGame og4 = new OnlineGame(1216,p2,p3,null,null,null,null,null,null,0,0,0,0,true,true,true,false,false,3,3,3,3,List.of(),List.of(),true,true);
    OnlineGame og5 = new OnlineGame(1217,p2,p3,null,null,null,null,null,null,0,0,0,0,true,true,true,false,false,3,3,3,3,List.of(),List.of(),true,true);
    OnlineGame og6 = new OnlineGame(1218,p2,p3,null,null,null,null,null,null,0,0,0,0,true,true,true,false,false,3,3,3,3,List.of(),List.of(),true,true);
    OnlineGame og7 = new OnlineGame(1219,p2,null,null,null,null,null,null,null,0,0,0,0,false,false,true,false,false,3,3,3,3,List.of(),List.of(),true,true);
    





    @BeforeEach
    public void setup(){
        og.setWinner(p.getUsername());
        og2.setWinner(p2.getUsername());
        og3.setWinner(p2.getUsername());
        og4.setWinner(p2.getUsername());
        og5.setWinner(p2.getUsername());
        og6.setWinner(p3.getUsername());
        og.setPlayer1Shifts(3);
        og.setPlayer2Shifts(3);
        og2.setPlayer1Shifts(4);
        og2.setPlayer2Shifts(5);
        og3.setPlayer1Shifts(8);
        og3.setPlayer2Shifts(3);
        og4.setPlayer1Shifts(3);
        og4.setPlayer2Shifts(6);
        og5.setPlayer1Shifts(7);
        og5.setPlayer2Shifts(5);
        og6.setPlayer1Shifts(9);
        og6.setPlayer2Shifts(4);
        playerService.save(p);
        playerService.save(p2);
        playerService.save(p3);
        onlineGameService.save(og);
        onlineGameService.save(og2);
        onlineGameService.save(og3);
        onlineGameService.save(og4);
        onlineGameService.save(og5);
        onlineGameService.save(og6);
        onlineGameService.save(og7);
    }

    @AfterEach
    public void tearDown(){
        onlineGameService.delete(og);
        onlineGameService.delete(og2);
        onlineGameService.delete(og3);
        onlineGameService.delete(og4);
        onlineGameService.delete(og5);
        onlineGameService.delete(og6);
        onlineGameService.delete(og7);
        playerService.delete(p);
        playerService.delete(p2);
        playerService.delete(p3);
    }

    @Test
    public void testDelete(){
        onlineGameService.delete(og);
        assert(onlineGameService.getOnlineGameByid(1213).isEmpty());
    }

    @Test
    public void testGetNotStartedGames(){
        List<OnlineGame> games = onlineGameService.getNotStartedGames();
        assertEquals(games.size(), 1);
    }

    @Test
    public void testGetNumOfGames(){
        assert(this.onlineGameService.getNumOfGames()==6);
    }

    @Test
    public void testGetNumOfWinsByPlayer1(){
        int res = this.onlineGameService.getNumOfWinsByPlayer1();
        assertEquals(res, 4);
    }

    @Test
    public void testGetNumOfWinsByPlayer2(){
        int res = this.onlineGameService.getNumOfWinsByPlayer2();
        assertEquals(res, 2);
    }

    @Test
    public void testGetNumOfPerfectWins(){
        int res = this.onlineGameService.getNumOfPerfectWins();
        assertEquals(res, 3);
    }

    @Test
    public void testGetAverageOfShiftsToWinByPlayer1(){
        double res = this.onlineGameService.getAverageOfShiftsToWinByPlayer1();
        assertEquals(5.25, res, 0.001);
    }

    @Test
    public void testGetAverageOfShiftsToWinByPlayer2(){
        double res = this.onlineGameService.getAverageOfShiftsToWinByPlayer2();
        assertEquals(4.5, res, 0.001);
    }

    @Test
    public void testGetNumOfGamesFromUser(){
        int res = this.onlineGameService.getNumOfGamesFromUser("María");
        assertEquals(2, res);
    }

    @Test
    public void testGetNumOfWinsFromUser(){
        int res = this.onlineGameService.getNumOfWinsFromUser("María");
        assertEquals(1, res);
    }

    @Test
    public void testGetNumOfPerfectWinsFromUser(){
        int res = this.onlineGameService.getNumOfPerfectWinsFromUser("María");
        assertEquals(1, res);
    }

    @Test
    public void testGetAverageNumOfShiftsToWinByUser(){
        double res = this.onlineGameService.getAverageOfShiftsToWinByPlayer1FromUser("María");
        assertEquals(3.0, res, 0.001);
    }

    @Test
    public void testGetWinRate(){
        String res = this.onlineGameService.getWinRate("María");
        assertEquals(res, "50,00%");
    }

    @Test
    public void testPerfectWinsRate(){
        String res = this.onlineGameService.perfectWinsRate("María");
        assertEquals(res, "50,00%");
    }

    @Test
    public void testGetRankingGame(){
        Map<String,Long> res = this.onlineGameService.getRankingGame();
        assertEquals(res.size(), 3);
        int maria = res.get("María").intValue();
        assertEquals(maria, 2);
        int maria2 = res.get("María2").intValue();
        assertEquals(maria2, 6);
        int maria3 = res.get("María3").intValue();
        assertEquals(maria3, 4);
    }

    @Test
    public void testGetRankingWin(){
        Map<String,Long> res = this.onlineGameService.getRankingWin();
        assertEquals(res.size(), 3);
        int maria = res.get("María").intValue();
        assertEquals(maria, 1);
        int maria2 = res.get("María2").intValue();
        assertEquals(maria2, 4);
        int maria3 = res.get("María3").intValue();
        assertEquals(maria3, 1);
    }
   
}
