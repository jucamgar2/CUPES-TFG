package TFG.CUPES.GameAloneTest;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.stereotype.Service;

import TFG.CUPES.entities.GameAlone;
import TFG.CUPES.entities.Player;
import TFG.CUPES.services.GameAloneService;
import TFG.CUPES.services.PlayerService;


@DataJpaTest(includeFilters = @Filter(Service.class))
public class GameAloneServiceTest {
    
    @Autowired
    private GameAloneService gameAloneService;

    @Autowired
    private PlayerService playerService;


    Player p = new Player("María","1111",true,LocalDate.of(2015,1,1),"maria@maria.com","María");
    Player p2 = new Player("María2","1111",true,LocalDate.of(2015,1,1),"maria2@maria2.com","María2");
    GameAlone g = new GameAlone(5555,null,1,true,true,0,0,"token",p,null);
    GameAlone g2 = new GameAlone(5556,null,4,true,false,0,0,"token",p,null);
    GameAlone g3 = new GameAlone(5557,null,3,true,true,0,0,"token",p,null);
    GameAlone g4 = new GameAlone(5558,null,4,true,true,0,0,"token",p,null);
    GameAlone g5 = new GameAlone(5559,null,2,true,true,0,0,"token",p,null);
    GameAlone g6 = new GameAlone(5560,null,1,true,true,0,0,"token",p,null);
    GameAlone g7 = new GameAlone(5561,null,1,true,true,0,0,"token",p2,null);
    GameAlone g8 = new GameAlone(5562,null,1,true,true,0,0,"token",p2,null);
    
    
    @BeforeEach
    public void setUp(){
        playerService.save(p);
        playerService.save(p2);
        gameAloneService.saveGame(g);
        gameAloneService.saveGame(g2);
        gameAloneService.saveGame(g3);
        gameAloneService.saveGame(g4);
        gameAloneService.saveGame(g5);
        gameAloneService.saveGame(g6);
        gameAloneService.saveGame(g7);
        gameAloneService.saveGame(g8);
    }

    
    @AfterEach
    public void tearDown(){
        playerService.delete(p);
        playerService.delete(p2);
        gameAloneService.deleteGame(g);
        gameAloneService.deleteGame(g2);
        gameAloneService.deleteGame(g3);
        gameAloneService.deleteGame(g4);
        gameAloneService.deleteGame(g5);
        gameAloneService.deleteGame(g6);
        gameAloneService.deleteGame(g7);
        gameAloneService.deleteGame(g8);
    }

    @Test
    public void testDeleteGame(){
        gameAloneService.deleteGame(g8);
        assert(gameAloneService.getGameById(5562).isEmpty());
        assert(gameAloneService.getGameByTokenAndId( "token",5562).isEmpty());
    }

    @Test
    public void testGetNumOfGamesFromUser(){
        assert(gameAloneService.getNumOfGamesFromUser("María")==6);
    }

    @Test
    public void testGetNumOfWinsFromUser(){
        assert(gameAloneService.getNumOfWinsFromUser("María")==5);
    }

    @Test
    public void testGetNumOfWinsWihtOneShiftFromUser(){
        assert(gameAloneService.getNumOfWinsWihtOneShiftFromUser("María")==2);
    }

    @Test
    public void testGetNumOfWinsWithTwoShiftsFromUser(){
        assert(gameAloneService.getNumOfWinsWithTwoShiftsFromUser("María")==1);
    }

    @Test
    public void testGetNumOfWinsWithThreeShiftsFromUser(){
        assert(gameAloneService.getNumOfWinsWithThreeShiftsFromUser("María")==1);
    }

    @Test
    public void testGetNumOfWinsWithFourShiftsFromUser(){
        assert(gameAloneService.getNumOfWinsWithFourShiftsFromUser("María")==1);
    }

    @Test
    public void testGetAverageShiftsToWinFromUser(){
        assert(gameAloneService.getAverageShiftsTowinFromUser("María")==2.20);
    }

    @Test
    public void testGetWinRateFromUser(){
        assert(gameAloneService.getWinRateFromUser("María")==0.8333333333333334);
    }

    @Test
    public void testGetNumOfFinishGames(){
        assert(gameAloneService.getNumOfFinishGames()==8);
    }

    @Test
    public void testGetNumOfWins(){
        assert(gameAloneService.getNumOfWins()==7);
    }

    @Test
    public void testGetNumOfWinsWihtOneShift(){
        assert(gameAloneService.getNumOfWinsWihtOneShift()==4);
    }

    @Test
    public void testGetNumOfWinsWithTwoShifts(){
        assert(gameAloneService.getNumOfWinsWithTwoShifts()==1);
    }

    @Test
    public void testGetNumOfWinsWithThreeShifts(){
        assert(gameAloneService.getNumOfWinsWithThreeShifts()==1);
    }

    @Test
    public void testGetNumOfWinsWithFourShifts(){
        assert(gameAloneService.getNumOfWinsWithFourShifts()==1);
    }

    @Test
    public void testGetAverageShiftsToWin(){
        Double r = gameAloneService.getAverageShiftsTowin();
        Double r2 = 1.8571428571428572;
        assertEquals(r, r2);
    }	

    @Test
    public void testGetWinRate(){
        Double r = gameAloneService.getWinRate();
        Double r2 = 0.875;
        assertEquals(r,r2);
    }

    @Test
    public void testGetRankingGame(){
        Map<String, Long> ranking = gameAloneService.getRankingGame();
        assert(ranking.get("María")==6);
        assert(ranking.get("María2")==2);
    }

    @Test
    public void testGetRankingWin(){
        Map<String, Long> ranking = gameAloneService.getRankingWin();
        assert(ranking.get("María")==5);
        assert(ranking.get("María2")==2);
    }    
}
