package TFG.CUPES.MultiplayerGameTest;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import TFG.CUPES.entities.LocalGame;

@ActiveProfiles("test")
public class MultiplayerGameTest {

    @Test
    public void multiPlayerGameTest(){
        LocalGame lg = new LocalGame();
        lg.setPlayer1Shifts(0);
        lg.setPlayer2Shifts(0);
        LocalDateTime date = LocalDateTime.now();
        lg.setPlayer1Start(date);
        lg.setPlayer2Start(date);
        lg.setPlayer1FInish(date);
        lg.setPlayer2Finish(date);
        lg.setPlayer1CanWin(true);
        lg.setPlayer2CanWin(true);
        lg.setWinner("Joselillo");
        assert(lg.getPlayer1Shifts()==0);
        assert(lg.getPlayer2Shifts()==0);
        assert(lg.getPlayer1Start()==date);
        assert(lg.getPlayer2Start()==date);
        assert(lg.getPlayer1FInish()==date);
        assert(lg.getPlayer2Finish()==date);
        assert(lg.getPlayer1CanWin()==true);
        assert(lg.getPlayer2CanWin()==true);
        assert(lg.getWinner()=="Joselillo");
    }

    @Test
    public void testCheckWinner1(){
        LocalGame lg = new LocalGame();
        lg.setPlayer1CanWin(false);
        lg.setPlayer2CanWin(false);
        assert(lg.checkWinner("Joselillo", "Pepito")=="Empate");
    }
    
    @Test
    public void testCheckWinner2(){
        LocalGame lg = new LocalGame();
        lg.setPlayer1CanWin(false);
        lg.setPlayer2CanWin(true);
        assert(lg.checkWinner("Joselillo", "Pepito")=="Pepito");
    }

    @Test
    public void testCheckWinner3(){
        LocalGame lg = new LocalGame();
        lg.setPlayer1CanWin(true);
        lg.setPlayer2CanWin(false);
        assert(lg.checkWinner("Joselillo", "Pepito")=="Joselillo");
    }

    @Test
    public void testCheckWinner4(){
        LocalGame lg = new LocalGame();
        lg.setPlayer1CanWin(true);
        lg.setPlayer2CanWin(true);
        lg.setPlayer1Shifts(1);
        lg.setPlayer2Shifts(2);
        assert(lg.checkWinner("Joselillo", "Pepito")=="Joselillo");
    }

    @Test
    public void testCheckWinner5(){
        LocalGame lg = new LocalGame();
        lg.setPlayer1CanWin(true);
        lg.setPlayer2CanWin(true);
        lg.setPlayer1Shifts(2);
        lg.setPlayer2Shifts(1);
        assert(lg.checkWinner("Joselillo", "Pepito")=="Pepito");
    }

    @Test
    public void testCheckWinner6(){
        LocalGame lg = new LocalGame();
        lg.setPlayer1CanWin(true);
        lg.setPlayer2CanWin(true);
        lg.setPlayer1Shifts(1);
        lg.setPlayer2Shifts(1);
        LocalDateTime date = LocalDateTime.now();
        lg.setPlayer1Start(date);
        lg.setPlayer2Start(date);
        lg.setPlayer1FInish(date.plusSeconds(1));
        lg.setPlayer2Finish(date.plusSeconds(2));
        assert(lg.checkWinner("Joselillo", "Pepito")=="Joselillo");
    }

    @Test
    public void testCheckWinner7(){
        LocalGame lg = new LocalGame();
        lg.setPlayer1CanWin(true);
        lg.setPlayer2CanWin(true);
        lg.setPlayer1Shifts(1);
        lg.setPlayer2Shifts(1);
        LocalDateTime date = LocalDateTime.now();
        lg.setPlayer1Start(date);
        lg.setPlayer2Start(date);
        lg.setPlayer1FInish(date.plusSeconds(2));
        lg.setPlayer2Finish(date.plusSeconds(1));
        assert(lg.checkWinner("Joselillo", "Pepito")=="Pepito");
    }

    @Test
    public void testCheckWinner8(){
        LocalGame lg = new LocalGame();
        lg.setPlayer1CanWin(true);
        lg.setPlayer2CanWin(true);
        lg.setPlayer1Shifts(1);
        lg.setPlayer2Shifts(1);
        LocalDateTime date = LocalDateTime.now();
        lg.setPlayer1Start(date);
        lg.setPlayer2Start(date);
        lg.setPlayer1FInish(date.plusSeconds(1));
        lg.setPlayer2Finish(date.plusSeconds(1));
        assert(lg.checkWinner("Joselillo", "Pepito")=="Empate");
    }
}
