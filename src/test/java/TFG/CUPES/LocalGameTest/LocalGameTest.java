package TFG.CUPES.LocalGameTest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import TFG.CUPES.entities.LocalGame;


@ActiveProfiles("test")
public class LocalGameTest {
    
    

    @Test
    public void testCheckLocalGame(){
        List<LocalGame> games = new ArrayList<>();
        LocalGame lg1 = new LocalGame(1,null,"Juanillo",null,null,"Joselillo",1,1,"token",List.of(),List.of());
        LocalGame lg2 = new LocalGame(2,"Joselillo",null,null,null,"Joselillo",1,1,"token",List.of(),List.of());
        LocalGame lg3 = new LocalGame(3,"J","juanillo",null,null,"joselillo",1,1,"token",List.of(),List.of());
        LocalGame lg4 = new LocalGame(4,"Joselillo","j",null,null,"joselillo",1,1,"token",List.of(),List.of());
        LocalGame lg5 = new LocalGame(5,"Joselillo","juanilloaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",null,null,"j",1,1,"token",List.of(),List.of());
        LocalGame lg6 = new LocalGame(6,"Joselilloaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa","juanillo",null,null,"j",1,1,"token",List.of(),List.of());
        LocalGame lg7 = new LocalGame(7,"joselu","joselu",null,null,"j",1,1,"token",List.of(),List.of());
        games.add(lg6);
        games.add(lg5);
        games.add(lg4);
        games.add(lg3);
        games.add(lg2);
        games.add(lg1);
        games.add(lg7);
        for(LocalGame lg: games){
            List<String> res = lg.chekcLocalGame();
            assertEquals(1,res.size());
        }
    }
}
