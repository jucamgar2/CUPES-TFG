package TFG.CUPES.LocalGameTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.stereotype.Service;

import TFG.CUPES.entities.Image;
import TFG.CUPES.entities.LocalGame;
import TFG.CUPES.services.ImageService;
import TFG.CUPES.services.LocalGameService;

@DataJpaTest(includeFilters = @Filter(Service.class))
public class LocalGameServiceTest {

    @Autowired
    private LocalGameService localGameService;

    @Autowired
    private ImageService imageService;

    @Test
    public void testSaveLocalGame(){
        Image i1 = imageService.getLogoById(34);
        Image i2 = imageService.getLogoById(35);
        LocalGame localGame = new LocalGame(5580,"Joselillo","Juanillo",i1,i2,"Joselillo",1,1,"token",List.of(),List.of());
        localGame.setId(5580);
        localGame.setPlayer1Name("Joselillo");
        localGame.setPlayer2Name("Juanillo");
        localGame.setPlayer1Image(i1);
        localGame.setPlayer2Image(i2);
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
        Image i1 = imageService.getLogoById(34);
        Image i2 = imageService.getLogoById(35);
        LocalGame lg = new LocalGame(5581,"Joselillo","Juanillo",i1,i2,"Joselillo",1,1,"token",List.of(),List.of());
        this.localGameService.save(lg);
        LocalGame localGame = this.localGameService.getLocalGameById(5581).orElse(lg);
        assertNotEquals(localGame,null);
        assertEquals(localGame.getPlayer1Name(),"Joselillo");
        assertEquals(localGame.getPlayer2Name(),"Juanillo");
        assert(localGame.getPlayer1Image().getId()==34);
        assert(localGame.getPlayer2Image().getId()==35);
        assertEquals(localGame.getActualPlayer(),"Joselillo");
        assert(localGame.getX()==1);
        assert(localGame.getY()==1);
        assertEquals(localGame.getToken(),"token");
        assertEquals(localGame.getPlayer1Positions(),List.of());
        assertEquals(localGame.getPlayer2Positions(),List.of());
    }

    @Test
    public void testGetLocalGameByTokenAndId(){
        Image i1 = imageService.getLogoById(34);
        Image i2 = imageService.getLogoById(35);
        LocalGame lg = new LocalGame(5582,"Joselillo","Juanillo",i1,i2,"Joselillo",1,1,"token",List.of(),List.of());
        this.localGameService.save(lg);
        LocalGame localGame = this.localGameService.getLocalGameByTokenAndId("token", 5582).orElse(lg);
        assertNotEquals(localGame,null);
        assertEquals(localGame.getPlayer1Name(),"Joselillo");
        assertEquals(localGame.getPlayer2Name(),"Juanillo");
        assert(localGame.getPlayer1Image().getId()==34);
        assert(localGame.getPlayer2Image().getId()==35);
        assertEquals(localGame.getActualPlayer(),"Joselillo");
        assert(localGame.getX()==1);
        assert(localGame.getY()==1);
        assertEquals(localGame.getToken(),"token");
    }
    
}
