package TFG.CUPES.OnlineGameTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import TFG.CUPES.entities.OnlineGame;
import TFG.CUPES.entities.Player;
import TFG.CUPES.services.OnlineGameService;
import TFG.CUPES.services.PlayerService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OnlineGameRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OnlineGameService onlineGameService;

    @Autowired
    private PlayerService playerService;

    @Test
    public void checkGameStatusTest() throws Exception{
        OnlineGame og = new OnlineGame();
        og.setPlayer1FInish(LocalDateTime.now());
        og.setPlayer2Finish(LocalDateTime.now());
        onlineGameService.save(og);
        mockMvc.perform(get("/stand/"+og.getId()))
        .andExpect(status().isOk())
        .andExpect(content().string("true"));
        onlineGameService.delete(og);
    }

    @Test
    public void checkGameStatusTest2() throws Exception{
        OnlineGame og = new OnlineGame();
        og.setPlayer1Leaves(true);
        onlineGameService.save(og);
        mockMvc.perform(get("/stand/"+og.getId()))
        .andExpect(status().isOk())
        .andExpect(content().string("true"));
        onlineGameService.delete(og);
    }

    @Test
    public void checkGameStatusTest3() throws Exception{
        OnlineGame og = new OnlineGame();
        og.setPlayer2Leaves(true);
        og.setPlayer1Leaves(false);
        onlineGameService.save(og);
        mockMvc.perform(get("/stand/"+og.getId()))
        .andExpect(status().isOk())
        .andExpect(content().string("true"));
        onlineGameService.delete(og);
    }

    @Test
    public void checkGameStatusTest4() throws Exception{
        OnlineGame og = new OnlineGame();
        og.setPlayer1Leaves(false);
        og.setPlayer2Leaves(false);
        onlineGameService.save(og);
        mockMvc.perform(get("/stand/"+og.getId()))
        .andExpect(status().isOk())
        .andExpect(content().string("false"));
        onlineGameService.delete(og);
    }

    @Test
    public void checkLobbyStatusTest() throws Exception{
        Player p1 = new Player();
        p1.setUsername("p1");
        p1.setPassword("p1");
        Player p2 = new Player();
        p2.setUsername("p2");
        p2.setPassword("p2");
        playerService.save(p1);
        playerService.save(p2);
        OnlineGame og = new OnlineGame();
        og.setPlayer1(p1);
        og.setPlayer2(p2);
        og.setPlayer1IsReady(true);
        og.setPlayer2IsReady(true);
        onlineGameService.save(og);
        mockMvc.perform(get("/start/"+og.getId()))
        .andExpect(status().isOk())
        .andExpect(content().string("true"));
        onlineGameService.delete(og);
    }

    @Test
    public void checkLobbyStatusTest2() throws Exception{
        OnlineGame og = new OnlineGame();
        og.setPlayer1IsReady(true);
        og.setPlayer2IsReady(true);
        onlineGameService.save(og);
        mockMvc.perform(get("/start/"+og.getId()))
        .andExpect(status().isOk())
        .andExpect(content().string("false"));
        onlineGameService.delete(og);
    }

    @Test
    public void checkLobbyStatusTest3() throws Exception{
        Player p1 = new Player();
        p1.setUsername("p1");
        p1.setPassword("p1");
        Player p2 = new Player();
        p2.setUsername("p2");
        p2.setPassword("p2");
        playerService.save(p1);
        playerService.save(p2);
        OnlineGame og = new OnlineGame();
        og.setPlayer1(p1);
        og.setPlayer2(p2);
        og.setPlayer1IsReady(false);
        og.setPlayer2IsReady(true);
        onlineGameService.save(og);
        mockMvc.perform(get("/start/"+og.getId()))
        .andExpect(status().isOk())
        .andExpect(content().string("false"));
        onlineGameService.delete(og);
    }

    @Test
    public void checkLobbyTest() throws Exception{
        OnlineGame og = new OnlineGame();
        this.onlineGameService.save(og);
        mockMvc.perform(get("/lobby"))
        .andExpect(status().isOk())
        .andExpect(content().string("true"));
        onlineGameService.delete(og);
    }

    @Test
    public void checkLobbyTest2() throws Exception{
        mockMvc.perform(get("/lobby"))
        .andExpect(status().isOk())
        .andExpect(content().string("false"));
    }
    
}
