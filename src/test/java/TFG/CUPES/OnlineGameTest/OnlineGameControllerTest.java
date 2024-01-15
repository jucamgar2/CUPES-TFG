package TFG.CUPES.OnlineGameTest;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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
        mockMvc.perform(get("/game/onlineGame/new"))
        .andExpect(status().is3xxRedirection())
        .andReturn();
        List<OnlineGame> games = this.onlineGameService.getAllOnlineGames();
        assert(!games.isEmpty());
        games.forEach(x->this.onlineGameService.delete(x));
    }

    @Test
    @WithMockUser(username = "p1", password = "p1", authorities = {"player"})
    public void joinNoGamesTest() throws Exception{
        List<OnlineGame> games = this.onlineGameService.getAllOnlineGames();
        for(OnlineGame game : games){
            this.onlineGameService.delete(game);
        }
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
        og.setPlayer1Succes(3);
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

    @Test
    @WithMockUser(username = "p1", password = "p1", authorities = {"player"})
    public void player1LosesNoSuccessTest() throws Exception{
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
        og.setPlayer2Succes(4);
        og.setPlayer2Finish(LocalDateTime.now());
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
        .param("name","x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()));
        assertEquals(0, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name","x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()));
        assertEquals(0, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name","x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()));
        assertEquals(0, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name","x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()));
        assertEquals(0, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name","x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()));
        assertEquals(0, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name","x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()));
        assertEquals(0, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name","x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()));
        assertEquals(0, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name","x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()));
        assertEquals(0, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name","x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()));
        assertEquals(0, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name","x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()));
        assertEquals(0, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name","x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()));
        assertEquals(0, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name","x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()));
        assertEquals(0, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name","x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()));
        assertEquals(0, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name","x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()));
        assertEquals(0, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name","x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/stand/" + og.getId()));
        assertEquals(0, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        og = this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null);
        assertEquals(15, og.getPlayer1Shifts());
        mockMvc.perform(get("/game/onlineGame/stand/" + og.getId()))
        .andExpect(view().name("redirect:/game/onlineGame/finish/" + og.getId()));
        mockMvc.perform(get("/game/onlineGame/finish/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/finish"));
        og = this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null);
        assertNotNull(og.getWinner());
        this.onlineGameService.delete(og);
    }

    @Test
    @WithMockUser(username = "p2", password = "p2", authorities = {"player"})
    public void player2LosesNoSuccessTest() throws Exception{
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
        og.setPlayer1Succes(4);
        og.setPlayer1FInish(LocalDateTime.now());
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
        .param("name","x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()));
        assertEquals(0, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name","x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()));
        assertEquals(0, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name","x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()));
        assertEquals(0, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name","x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()));
        assertEquals(0, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name","x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()));
        assertEquals(0, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name","x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()));
        assertEquals(0, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name","x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()));
        assertEquals(0, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name","x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()));
        assertEquals(0, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name","x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()));
        assertEquals(0, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name","x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()));
        assertEquals(0, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name","x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()));
        assertEquals(0, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name","x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()));
        assertEquals(0, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name","x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()));
        assertEquals(0, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name","x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/play/" + og.getId()));
        assertEquals(0, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        mockMvc.perform(get("/game/onlineGame/play/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playOnlineGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"))
        .andExpect(model().attributeExists("imageUrl"));
        mockMvc.perform(post("/game/onlineGame/play/" + og.getId())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name","x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/onlineGame/stand/" + og.getId()));
        assertEquals(0, this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null).getPlayer1Succes());
        og = this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null);
        assertEquals(15, og.getPlayer2Shifts());
        mockMvc.perform(get("/game/onlineGame/stand/" + og.getId()))
        .andExpect(view().name("redirect:/game/onlineGame/finish/" + og.getId()));
        mockMvc.perform(get("/game/onlineGame/finish/" + og.getId()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/finish"));
        og = this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null);
        assertNotNull(og.getWinner());
        this.onlineGameService.delete(og);
    }

    @Test
    @WithMockUser(username = "p1", password = "p1", authorities = {"player"})
    public void leaveCreatedGameNotStartedTest() throws Exception{
        Player p1 = new Player("p1","p1",true,null,"p1","p1");
        this.playerService.save(p1);
        OnlineGame og = new OnlineGame();
        og.setPlayer1(p1);
        og.setPlayer1IsReady(false);
        og.setPlayer2IsReady(false);
        og.setGameStart(false);
        og.setCreationDate(LocalDateTime.now());
        this.onlineGameService.save(og);
        mockMvc.perform(get("/game/onlineGame/leave/" + og.getId()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/"))
        .andExpect(model().attributeExists("message"))
        .andExpect(model().attribute("message", "Has abandonado la partida y esta ha sido borrada ya que no había empezado"));
        og = this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null);
        assert(og==null);
    }

    @Test
    @WithMockUser(username = "p2", password = "p2", authorities = {"player"})
    public void leaveJoinedGameNotStartedTest() throws Exception{
        Player p1 = new Player("p1","p1",true,null,"p1","p1");
        Player p2 = new Player("p2","p2",true,null,"p2","p2");
        this.playerService.save(p1);
        this.playerService.save(p2);
        OnlineGame og = new OnlineGame();
        og.setPlayer1(p1);
        og.setPlayer2(p2);
        og.setGameStart(false);
        this.onlineGameService.save(og);
        mockMvc.perform(get("/game/onlineGame/leave/" + og.getId()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/"))
        .andExpect(model().attributeExists("message"))
        .andExpect(model().attribute("message", "Has abandonado la partida antes de que empezara, por lo que ahora queda un hueco"));
        this.onlineGameService.delete(og);
    }

    @Test
    @WithMockUser(username = "p1", password = "p1", authorities = {"player"})
    public void leavePlayer1StartedGameTest() throws Exception{
        Player p1 = new Player("p1","p1",true,null,"p1","p1");
        Player p2 = new Player("p2","p2",true,null,"p2","p2");
        this.playerService.save(p1);
        this.playerService.save(p2);
        OnlineGame og = new OnlineGame();
        og.setPlayer1(p1);
        og.setPlayer2(p2);
        og.setGameStart(true);
        og.setPlayer1Image1(this.imageService.getRandomLogo());
        og.setPlayer1Image2(this.imageService.getRandomLogo());
        og.setPlayer1Image3(this.imageService.getRandomLogo());
        og.setPlayer2Image1(this.imageService.getRandomLogo());
        og.setPlayer2Image2(this.imageService.getRandomLogo());
        og.setPlayer2Image3(this.imageService.getRandomLogo());
        this.onlineGameService.save(og);
        mockMvc.perform(get("/game/onlineGame/leave/" + og.getId()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/"))
        .andExpect(model().attributeExists("message"))
        .andExpect(model().attribute("message", "Has abandonado la partida y por tanto has perdido"));
        og = this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null);
        assert(og!=null);
        assert(og.getWinner()==og.getPlayer2().getUsername());
        mockMvc.perform(get("/game/onlineGame/finish/" + og.getId()))
        .andExpect(status().isOk());
        this.onlineGameService.delete(og);
    }

    @Test
    @WithMockUser(username = "p2", password = "p2", authorities = {"player"})
    public void leavePlayer2StartedGameTest() throws Exception{
        Player p1 = new Player("p1","p1",true,null,"p1","p1");
        Player p2 = new Player("p2","p2",true,null,"p2","p2");
        this.playerService.save(p1);
        this.playerService.save(p2);
        OnlineGame og = new OnlineGame();
        og.setPlayer1(p1);
        og.setPlayer2(p2);
        og.setGameStart(true);
        og.setPlayer1Leaves(false);
        og.setPlayer1Image1(this.imageService.getRandomLogo());
        og.setPlayer1Image2(this.imageService.getRandomLogo());
        og.setPlayer1Image3(this.imageService.getRandomLogo());
        og.setPlayer2Image1(this.imageService.getRandomLogo());
        og.setPlayer2Image2(this.imageService.getRandomLogo());
        og.setPlayer2Image3(this.imageService.getRandomLogo());
        this.onlineGameService.save(og);
        mockMvc.perform(get("/game/onlineGame/leave/" + og.getId()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/"))
        .andExpect(model().attributeExists("message"))
        .andExpect(model().attribute("message", "Has abandonado la partida y por tanto has perdido"));
        og = this.onlineGameService.getOnlineGameByid(og.getId()).orElse(null);
        assert(og!=null);
        assert(og.getWinner()==og.getPlayer1().getUsername());
        mockMvc.perform(get("/game/onlineGame/finish/" + og.getId()))
        .andExpect(status().isOk());
        this.onlineGameService.delete(og);
    }


}
