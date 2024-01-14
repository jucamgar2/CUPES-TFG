package TFG.CUPES.OnlineGameTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import TFG.CUPES.entities.Image;
import TFG.CUPES.entities.OnlineGame;
import TFG.CUPES.entities.Player;
import TFG.CUPES.services.ImageService;
import TFG.CUPES.services.OnlineGameService;
import TFG.CUPES.services.PlayerService;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OnlineGameControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OnlineGameService onlineGameService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ImageService imageService;

    @Test
    @WithMockUser(username = "p1", password = "p1", authorities = {"player"})
    public void testCreateGame() throws Exception {
        Player p1 = new Player("p1","p1",true,null,"p1","p1");
        this.playerService.save(p1);
        MvcResult mvcResult = mockMvc.perform(get("/game/onlineGame/new"))
        .andExpect(status().is3xxRedirection())
        .andReturn();
        String redirectedUrl = mvcResult.getResponse().getHeader("Location");
        assert(redirectedUrl.startsWith("/game/onlineGame/"));
        String[] urlParts = redirectedUrl.split("/");
        String part = urlParts[urlParts.length-1];
        Integer gameId = Integer.parseInt(String.valueOf(part.charAt(0)));
        OnlineGame game = onlineGameService.getOnlineGameByid(gameId).orElse(null);
        assert(game != null);
        assert(game.getPlayer1().getUsername().equals("p1"));
        this.onlineGameService.delete(game);
    }

    @Test
    @WithMockUser(username = "p1", password = "p1", authorities = {"player"})
    public void joinNoGamesTest() throws Exception{
        Player p1 = new Player("p1","p1",true,null,"p1","p1");
        this.playerService.save(p1);
        mockMvc.perform(get("/game/onlineGame/join"))
        .andExpect(status().isOk())
        .andExpect(view().name("game/join"));
    }

    @Test
    @WithMockUser(username = "p1", password = "p1", authorities = {"player"})
    public void joinTest() throws Exception{
        Player p1 = new Player("p1","p1",true,null,"p1","p1");
        Player p2 = new Player("p2","p2",true,null,"p2","p2");
        this.playerService.save(p1);
        this.playerService.save(p2);
        OnlineGame og = new OnlineGame();
        og.setPlayer1(p2);
        og.setPlayer1IsReady(false);
        og.setPlayer2IsReady(false);
        og.setGameStart(false);
        og.setCreationDate(LocalDateTime.now());
        this.onlineGameService.save(og);
        MvcResult mvcResult = mockMvc.perform(get("/game/onlineGame/join"))
        .andExpect(status().is3xxRedirection())
        .andReturn();
        String redirectedUrl = mvcResult.getResponse().getHeader("Location");
        assert(redirectedUrl.equals("/game/onlineGame/joinning/" + og.getId()));
        this.onlineGameService.delete(og);
    }

    @Test
    @WithMockUser(username = "p1", password = "p1", authorities = {"player"})
    public void joinningTest() throws Exception{
        Player p1 = new Player("p1","p1",true,null,"p1","p1");
        Player p2 = new Player("p2","p2",true,null,"p2","p2");
        this.playerService.save(p1);
        this.playerService.save(p2);
        OnlineGame og = new OnlineGame();
        og.setPlayer1(p2);
        og.setPlayer1IsReady(false);
        og.setPlayer2IsReady(false);
        og.setGameStart(false);
        og.setCreationDate(LocalDateTime.now());
        this.onlineGameService.save(og);
        MvcResult mvcResult = mockMvc.perform(get("/game/onlineGame/join"))
        .andExpect(status().is3xxRedirection())
        .andReturn();
        String redirectedUrl = mvcResult.getResponse().getHeader("Location");
        assert(redirectedUrl.equals("/game/onlineGame/joinning/" + og.getId()));
        mockMvc.perform(get("/game/onlineGame/joinning/" + og.getId()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/join/" + og.getId()));
        this.onlineGameService.delete(og);
    }

    @Test
    @WithMockUser(username = "p1", password = "p1", authorities = {"player"})
    public void joinningGameNotExistsTest() throws Exception{
        mockMvc.perform(get("/game/onlineGame/joinning/1"))
        .andExpect(status().isOk())
        .andExpect(view().name("game/selectMode"))
        .andExpect(model().attributeExists("errors"));
    }

    @Test
    @WithMockUser(username = "p1", password = "p1", authorities = {"player"})
    public void joinningGameWith2PlayersTest() throws Exception{
        Player p1 = new Player("p1","p1",true,null,"p1","p1");
        Player p2 = new Player("p2","p2",true,null,"p2","p2");
        Player p3 = new Player("p3","p3",true,null,"p3","p3");
        this.playerService.save(p1);
        this.playerService.save(p2);
        this.playerService.save(p3);
        OnlineGame og = new OnlineGame();
        og.setPlayer1(p2);
        og.setPlayer1IsReady(false);
        og.setPlayer2IsReady(false);
        og.setGameStart(false);
        og.setCreationDate(LocalDateTime.now());
        og.setPlayer2(p3);
        this.onlineGameService.save(og);
        mockMvc.perform(get("/game/onlineGame/joinning/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/selectMode"))
        .andExpect(model().attributeExists("errors"));
        this.onlineGameService.delete(og);
    }

    @Test
    @WithMockUser(username = "p1", password = "p1", authorities = {"player"})
    public void lobbyTest() throws Exception{
        Player p1 = new Player("p1","p1",true,null,"p1","p1");
        Player p2 = new Player("p2","p2",true,null,"p2","p2");
        this.playerService.save(p1);
        this.playerService.save(p2);
        OnlineGame og = new OnlineGame();
        og.setPlayer1(p2);
        og.setPlayer2(p1);
        og.setPlayer1IsReady(false);
        og.setPlayer2IsReady(false);
        og.setGameStart(false);
        og.setCreationDate(LocalDateTime.now());
        this.onlineGameService.save(og);
        mockMvc.perform(get("/game/onlineGame/join/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/lobby"))
        .andExpect(model().attributeExists("game"));
        this.onlineGameService.delete(og);
    }

    @Test
    @WithMockUser(username = "p1", password = "p1", authorities = {"player"})
    public void lobbyGameNotExistsTest() throws Exception{
        mockMvc.perform(get("/game/onlineGame/join/1"))
        .andExpect(status().isOk())
        .andExpect(view().name("game/selectMode"))
        .andExpect(model().attributeExists("errors"));
    }

    @Test
    @WithMockUser(username = "p1", password = "p1", authorities = {"player"})
    public void lobbyGameWith2PlayersTest() throws Exception{
        Player p1 = new Player("p1","p1",true,null,"p1","p1");
        Player p2 = new Player("p2","p2",true,null,"p2","p2");
        Player p3 = new Player("p3","p3",true,null,"p3","p3");
        this.playerService.save(p1);
        this.playerService.save(p2);
        this.playerService.save(p3);
        OnlineGame og = new OnlineGame();
        og.setPlayer1(p2);
        og.setPlayer1IsReady(false);
        og.setPlayer2IsReady(false);
        og.setGameStart(false);
        og.setCreationDate(LocalDateTime.now());
        og.setPlayer2(p3);
        this.onlineGameService.save(og);
        mockMvc.perform(get("/game/onlineGame/join/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/selectMode"))
        .andExpect(model().attributeExists("errors"));
        this.onlineGameService.delete(og);
    }

    @Test
    @WithMockUser(username = "p1", password = "p1", authorities = {"player"})
    public void lobbyWithStartedGameTest() throws Exception{
        Player p1 = new Player("p1","p1",true,null,"p1","p1");
        Player p2 = new Player("p2","p2",true,null,"p2","p2");
        this.playerService.save(p1);
        this.playerService.save(p2);
        OnlineGame og = new OnlineGame();
        og.setPlayer1(p2);
        og.setPlayer2(p1);
        og.setPlayer1IsReady(true);
        og.setPlayer2IsReady(true);
        og.setGameStart(true);
        og.setCreationDate(LocalDateTime.now());
        this.onlineGameService.save(og);
        mockMvc.perform(get("/game/onlineGame/join/" + og.getId()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()));
        this.onlineGameService.delete(og);
    }

    @Test
    @WithMockUser(username = "p1", password = "p1", authorities = {"player"})
    public void startGameNotExists() throws Exception{
        mockMvc.perform(get("/game/onlineGame/start/1"))
        .andExpect(status().isOk())
        .andExpect(view().name("game/selectMode"))
        .andExpect(model().attributeExists("errors"));
    }

    @Test
    @WithMockUser(username = "p1", password = "p1", authorities = {"player"})
    public void startGamePlayer2Test() throws Exception{
        Player p1 = new Player("p1","p1",true,null,"p1","p1");
        Player p2 = new Player("p2","p2",true,null,"p2","p2");
        this.playerService.save(p1);
        this.playerService.save(p2);
        OnlineGame og = new OnlineGame();
        og.setPlayer1(p2);
        og.setPlayer2(p1);
        og.setPlayer1IsReady(true);
        og.setPlayer2IsReady(true);
        og.setGameStart(true);
        og.setCreationDate(LocalDateTime.now());
        this.onlineGameService.save(og);
        mockMvc.perform(get("/game/onlineGame/start/" + og.getId()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/join/" + og.getId()));
        og = this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null);
        assert(og.getPlayer2IsReady());
        this.onlineGameService.delete(og);
    }

    @Test
    @WithMockUser(username = "p2", password = "p2", authorities = {"player"})
    public void startGamePlayer1Test() throws Exception{
        Player p1 = new Player("p1","p1",true,null,"p1","p1");
        Player p2 = new Player("p2","p2",true,null,"p2","p2");
        this.playerService.save(p1);
        this.playerService.save(p2);
        OnlineGame og = new OnlineGame();
        og.setPlayer1(p2);
        og.setPlayer2(p1);
        og.setPlayer1IsReady(true);
        og.setPlayer2IsReady(true);
        og.setGameStart(true);
        og.setCreationDate(LocalDateTime.now());
        og.setPlayer1IsReady(true);
        this.onlineGameService.save(og);
        mockMvc.perform(get("/game/onlineGame/start/" + og.getId()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/join/" + og.getId()));
        og = this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null);
        assert(og.getPlayer1IsReady());
        this.onlineGameService.delete(og);
    }

    @Test
    @WithMockUser(username = "p3", password = "p3", authorities = {"player"})
    public void startGamePlayerNotInGameTest() throws Exception{
        Player p1 = new Player("p1","p1",true,null,"p1","p1");
        Player p2 = new Player("p2","p2",true,null,"p2","p2");
        Player p3 = new Player("p3","p3",true,null,"p3","p3");
        this.playerService.save(p1);
        this.playerService.save(p2);
        this.playerService.save(p3);
        OnlineGame og = new OnlineGame();
        og.setPlayer1(p2);
        og.setPlayer2(p1);
        og.setPlayer1IsReady(true);
        og.setPlayer2IsReady(true);
        og.setGameStart(true);
        og.setCreationDate(LocalDateTime.now());
        og.setPlayer1IsReady(true);
        this.onlineGameService.save(og);
        mockMvc.perform(get("/game/onlineGame/start/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/selectMode"))
        .andExpect(model().attributeExists("errors"));
        this.onlineGameService.delete(og);
    }

    @Test
    @WithMockUser(username = "p1", password = "p1", authorities = {"player"})
    public void playGame3SuccessPlayer1() throws Exception {
        Player p1 = new Player("p1","p1",true,null,"p1","p1");
        Player p2 = new Player("p2","p2",true,null,"p2","p2");
        this.playerService.save(p1);
        this.playerService.save(p2);
        Image i = new Image();
        i.setId(1678);
        i.setName("test");
        i.setResourceName("OL");
        i.setEnabled(true);
        this.imageService.save(i);
        Image i2 = new Image();
        i2.setId(1679);
        i2.setName("test2");
        i2.setResourceName("OL");
        i2.setEnabled(true);
        this.imageService.save(i2);
        Image i3 = new Image();
        i3.setId(1680);
        i3.setName("test3");
        i3.setResourceName("OL");
        i3.setEnabled(true);
        this.imageService.save(i3);
        Image i4 = new Image();
        i4.setId(1681);
        i4.setName("test4");
        i4.setResourceName("OL");
        i4.setEnabled(true);
        this.imageService.save(i4);
        OnlineGame og = new OnlineGame();
        og.setPlayer1(p1);
        og.setPlayer2(p2);
        og.setPlayer1IsReady(true);
        og.setPlayer2IsReady(true);
        og.setGameStart(true);
        og.setCreationDate(LocalDateTime.now());
        og.setPlayer1IsReady(true);
        og.setPlayer2IsReady(true);
        og.setGameStart(true);
        og.setPlayer1Leaves(false);
        og.setPlayer1Succes(0);
        og.setPlayer2Leaves(false);
        this.onlineGameService.save(og);
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        og = this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null);
        assertNotNull(og);
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name",og.getPlayer1Image1().getName()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()+"?player1succes=true"));
        assertEquals(1, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", og.getPlayer1Image2().getName()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()+ "?player1succes=true"));
        assertEquals(2, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", og.getPlayer1Image3().getName()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/stand/" + og.getId()));
        assertEquals(3, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/stand/" + og.getId()))
        .andExpect(view().name("game/stand"));
        this.onlineGameService.delete(og);
    }

    @Test
    @WithMockUser(username = "p2", password = "p2", authorities = {"player"})
    public void playGame3SuccessPlayer2() throws Exception {
        Player p1 = new Player("p1","p1",true,null,"p1","p1");
        Player p2 = new Player("p2","p2",true,null,"p2","p2");
        this.playerService.save(p1);
        this.playerService.save(p2);
        OnlineGame og = new OnlineGame();
        og.setPlayer1(p1);
        og.setPlayer2(p2);
        og.setPlayer1IsReady(true);
        og.setPlayer2IsReady(true);
        og.setGameStart(true);
        og.setCreationDate(LocalDateTime.now());
        og.setPlayer1IsReady(true);
        og.setPlayer2IsReady(true);
        og.setGameStart(true);
        og.setPlayer1Leaves(false);
        og.setPlayer1Succes(0);
        og.setPlayer2Leaves(false);
        og.setPlayer1FInish(LocalDateTime.now());
        og.setPlayer1Succes(2);
        this.onlineGameService.save(og);
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        og = this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null);
        assertNotNull(og);
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name",og.getPlayer2Image1().getName()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()+"?player2succes=true"));
        assertEquals(1, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer2Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", og.getPlayer2Image2().getName()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()+ "?player2succes=true"));
        assertEquals(2, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer2Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", og.getPlayer2Image3().getName()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/stand/" + og.getId()));
        assertEquals(3, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer2Succes());
        mockMvc.perform(get("/game/onlineGame/stand/" + og.getId()))
        .andExpect(view().name("redirect:/game/onlineGame/finish/" + og.getId()));
        mockMvc.perform(get("/game/onlineGame/finish/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/finish"));
        this.onlineGameService.delete(og);
    }


}
