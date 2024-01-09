package TFG.CUPES.LocalGameTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;

import TFG.CUPES.entities.LocalGame;
import TFG.CUPES.services.LocalGameService;

@DataJpaTest(includeFilters = @Filter(Service.class))
@ActiveProfiles("test")
public class LocalGameServiceTest {

    @Autowired
    private LocalGameService localGameService;

    @Test
    public void testSaveLocalGame(){
        LocalGame localGame = new LocalGame(5580,"Joselillo","Juanillo",null,null,"Joselillo",1,1,"token",List.of(),List.of());
        localGame.setId(5580);
        localGame.setPlayer1Name("Joselillo");
        localGame.setPlayer2Name("Juanillo");
        localGame.setPlayer1Image(null);
        localGame.setPlayer2Image(null);
        localGame.setActualPlayer("Joselillo");
        localGame.setX(1);
        localGame.setY(1);
        localGame.setToken("token");
        localGame.setPlayer1Positions(List.of());
        localGame.setPlayer2Positions(List.of());
        this.localGameService.save(localGame);
        
    }

    @Test
    public void testGetLocalGameById(){
        LocalGame lg = new LocalGame(5581,"Joselillo","Juanillo",null,null,"Joselillo",1,1,"token",List.of(),List.of());
        this.localGameService.save(lg);
        LocalGame localGame = this.localGameService.getLocalGameById(5581).orElse(lg);
        assertNotEquals(localGame,null);
        assertEquals(localGame.getPlayer1Name(),"Joselillo");
        assertEquals(localGame.getPlayer2Name(),"Juanillo");
        assertEquals(localGame.getActualPlayer(),"Joselillo");
        assert(localGame.getX()==1);
        assert(localGame.getY()==1);
        assertEquals(localGame.getToken(),"token");
        assertEquals(localGame.getPlayer1Positions(),List.of());
        assertEquals(localGame.getPlayer2Positions(),List.of());
    }

    @Test
    public void testGetLocalGameByTokenAndId(){
        LocalGame lg = new LocalGame(5582,"Joselillo","Juanillo",null,null,"Joselillo",1,1,"token",List.of(),List.of());
        this.localGameService.save(lg);
        LocalGame localGame = this.localGameService.getLocalGameByTokenAndId("token", 5582).orElse(lg);
        assertNotEquals(localGame,null);
        assertEquals(localGame.getPlayer1Name(),"Joselillo");
        assertEquals(localGame.getPlayer2Name(),"Juanillo");
        assertEquals(localGame.getActualPlayer(),"Joselillo");
        assert(localGame.getX()==1);
        assert(localGame.getY()==1);
        assertEquals(localGame.getToken(),"token");
    }
    
}
