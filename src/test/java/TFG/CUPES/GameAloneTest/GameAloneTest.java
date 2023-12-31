package TFG.CUPES.GameAloneTest;

import org.junit.jupiter.api.Test;

import TFG.CUPES.entities.GameAlone;
import TFG.CUPES.entities.Player;


public class GameAloneTest {

    @Test
    public void testGameAlone(){
        Player p = new Player();
        GameAlone ga = new GameAlone();
        ga.setId(897);
        ga.setPlayer(p);
        ga.setIsFinish(false);
        ga.setPositions(null);
        ga.setShift(1);
        ga.setWin(true);
        ga.setToken("token");
        ga.setX(0);
        ga.setY(1);
        ga.setSelected(null);
        assert(ga.getId()==897);
        assert(ga.getPlayer()==p);
        assert(ga.getIsFinish()==false);
        assert(ga.getPositions()==null);
        assert(ga.getShift()==1);
        assert(ga.getWin()==true);
        assert(ga.getToken()=="token");
        assert(ga.getX()==0);
        assert(ga.getY()==1);
        assert(ga.getSelected()==null);
    }
    
}
