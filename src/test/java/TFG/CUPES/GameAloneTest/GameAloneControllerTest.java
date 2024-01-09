package TFG.CUPES.GameAloneTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import TFG.CUPES.entities.GameAlone;
import TFG.CUPES.entities.Player;
import TFG.CUPES.services.GameAloneService;
import TFG.CUPES.services.PlayerService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class GameAloneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameAloneService gameAloneService;

    @Autowired
    private PlayerService playerService;

    @Test
    public void selectModeTest() throws Exception{
        this.mockMvc.perform(get("/game"))
         .andExpect(status().isOk())
         .andExpect(view().name("game/selectMode"));
    }

    @Test 
    public void testCreateGame() throws Exception{
         MvcResult mvcResult = this.mockMvc.perform(get("/game/new"))
         .andExpect(status().is3xxRedirection())
         .andReturn();
        String redirectedUrl = mvcResult.getResponse().getHeader("Location");
        assertNotNull(redirectedUrl);
        assertTrue(redirectedUrl.startsWith("/game/play/"));
        String[] urlParts = redirectedUrl.split("/");
        String part = urlParts[urlParts.length-1];
        Integer gameId = Integer.parseInt(String.valueOf(part.charAt(0)));
        GameAlone game = gameAloneService.getGameById(gameId).orElse(null);
        assertNotNull(game);
        this.gameAloneService.deleteGame(game);
    }

    @Test
    @WithMockUser(username="Guaje",authorities = {"player"})
    public void testCreateGameLogged() throws Exception{
        MvcResult mvcResult = this.mockMvc.perform(get("/game/new"))
        .andExpect(status().is3xxRedirection())
        .andReturn();
        String redirectedUrl = mvcResult.getResponse().getHeader("Location");
        assertNotNull(redirectedUrl);
        assertTrue(redirectedUrl.startsWith("/game/play/"));
        String[] urlParts = redirectedUrl.split("/");
        String part = urlParts[urlParts.length-1];
        Integer gameId = Integer.parseInt(String.valueOf(part.charAt(0)));
        GameAlone game = gameAloneService.getGameById(gameId).orElse(null);
        assertNotNull(game);
        assertNotNull(game.getPlayer());
        this.gameAloneService.deleteGame(game);
    }

    @Test
    public void testPlayGameNotExists() throws Exception{
        this.mockMvc.perform(get("/game/play/100"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("game/selectMode"))
        .andExpect(model().attributeExists("errors"));
    }

    @Test
    public void testCheckGameNotExists() throws Exception{
        this.mockMvc.perform(post("/game/play/100")
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", "x"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("game/selectMode"))
        .andExpect(model().attributeExists("errors"));
    }

    @Test
    public void testPlayGameWithTokenInvalid() throws Exception{
        this.mockMvc.perform(get("/game/play/1?token=123"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("game/selectMode"))
        .andExpect(model().attributeExists("errors"));
    }

    @Test
    public void testCheckGameNotTokenInvalid() throws Exception{
        this.mockMvc.perform(post("/game/play/100?token=123")
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", "x"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("game/selectMode"))
        .andExpect(model().attributeExists("errors"));
    }

    @Test
    public void playGameWin1Shift() throws Exception{
        MvcResult mvcResult = this.mockMvc.perform(get("/game/new"))
        .andExpect(status().is3xxRedirection())
        .andReturn();
        String redirectedUrl = mvcResult.getResponse().getHeader("Location");
        assertNotNull(redirectedUrl);
        assertTrue(redirectedUrl.startsWith("/game/play/"));
        String[] urlParts = redirectedUrl.split("/");
        String part = urlParts[urlParts.length-1];
        Integer gameId = Integer.parseInt(String.valueOf(part.charAt(0)));
        GameAlone game = gameAloneService.getGameById(gameId).orElse(null);
        assertNotNull(game);
        this.mockMvc.perform(get("/game/play/"+game.getId()+"?token="+game.getToken()))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("game/playGame"))
        .andExpect(model().attributeExists("logo"))
        .andReturn();
        MvcResult mvcResult3 = this.mockMvc.perform(post("/game/play/"+game.getId()+"?token="+game.getToken())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", game.getSelected().getName()))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/game/play/"+game.getId()+"?token="+game.getToken()))
        .andReturn();
        String redirectedUrl3 = mvcResult3.getResponse().getHeader("Location");
        assertNotNull(redirectedUrl3);
        this.mockMvc.perform(get(redirectedUrl3))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("game/gameResult"))
        .andReturn();
        game = this.gameAloneService.getGameById(gameId).orElse(null);
        assertNotNull(game);
        assertTrue(game.getWin());
        this.gameAloneService.deleteGame(game);
    }

    @Test
    public void playGameWin2Shift() throws Exception{
        MvcResult mvcResult = this.mockMvc.perform(get("/game/new"))
        .andExpect(status().is3xxRedirection())
        .andReturn();
        String redirectedUrl = mvcResult.getResponse().getHeader("Location");
        assertNotNull(redirectedUrl);
        assertTrue(redirectedUrl.startsWith("/game/play/"));
        String[] urlParts = redirectedUrl.split("/");
        String part = urlParts[urlParts.length-1];
        Integer gameId = Integer.parseInt(String.valueOf(part.charAt(0)));
        GameAlone game = gameAloneService.getGameById(gameId).orElse(null);
        assertNotNull(game);
        this.mockMvc.perform(get("/game/play/"+game.getId()+"?token="+game.getToken()))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("game/playGame"))
        .andExpect(model().attributeExists("logo"))
        .andReturn();
        this.mockMvc.perform(post("/game/play/"+game.getId()+"?token="+game.getToken())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", "x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/game/play/"+game.getId()+"?token="+game.getToken()))
        .andReturn();
        MvcResult mvcResult4 = this.mockMvc.perform(post("/game/play/"+game.getId()+"?token="+game.getToken())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", game.getSelected().getName()))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/game/play/"+game.getId()+"?token="+game.getToken()))
        .andReturn();
        String redirectedUrl4 = mvcResult4.getResponse().getHeader("Location");
        assertNotNull(redirectedUrl4);
        this.mockMvc.perform(get(redirectedUrl4))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("game/gameResult"))
        .andReturn();
        game = this.gameAloneService.getGameById(gameId).orElse(null);
        assertNotNull(game);
        assertTrue(game.getWin());
        this.gameAloneService.deleteGame(game);
    }

    @Test
    public void playGameLoseTest() throws Exception{
        MvcResult mvcResult = this.mockMvc.perform(get("/game/new"))
        .andExpect(status().is3xxRedirection())
        .andReturn();
        String redirectedUrl = mvcResult.getResponse().getHeader("Location");
        assertNotNull(redirectedUrl);
        assertTrue(redirectedUrl.startsWith("/game/play/"));
        String[] urlParts = redirectedUrl.split("/");
        String part = urlParts[urlParts.length-1];
        Integer gameId = Integer.parseInt(String.valueOf(part.charAt(0)));
        GameAlone game = gameAloneService.getGameById(gameId).orElse(null);
        assertNotNull(game);
        this.mockMvc.perform(get("/game/play/"+game.getId()+"?token="+game.getToken()))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("game/playGame"))
        .andExpect(model().attributeExists("logo"))
        .andReturn();
        this.mockMvc.perform(post("/game/play/"+game.getId()+"?token="+game.getToken())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", "x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/game/play/"+game.getId()+"?token="+game.getToken()))
        .andReturn();
        this.mockMvc.perform(get("/game/play/"+game.getId()+"?token="+game.getToken()))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("game/playGame"))
        .andExpect(model().attributeExists("logo"))
        .andReturn();
        this.mockMvc.perform(post("/game/play/"+game.getId()+"?token="+game.getToken())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", "x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/game/play/"+game.getId()+"?token="+game.getToken()))
        .andReturn();
        this.mockMvc.perform(get("/game/play/"+game.getId()+"?token="+game.getToken()))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("game/playGame"))
        .andExpect(model().attributeExists("logo"))
        .andReturn();
        this.mockMvc.perform(post("/game/play/"+game.getId()+"?token="+game.getToken())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", "x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/game/play/"+game.getId()+"?token="+game.getToken()))
        .andReturn();
        this.mockMvc.perform(get("/game/play/"+game.getId()+"?token="+game.getToken()))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("game/playGame"))
        .andExpect(model().attributeExists("logo"))
        .andReturn();
        MvcResult mvcResult4 = this.mockMvc.perform(post("/game/play/"+game.getId()+"?token="+game.getToken())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", "x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/game/play/"+game.getId()+"?token="+game.getToken()))
        .andReturn();
        String redirectedUrl4 = mvcResult4.getResponse().getHeader("Location");
        assertNotNull(redirectedUrl4);
        this.mockMvc.perform(get(redirectedUrl4))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("game/gameResult"))
        .andReturn();
        game = this.gameAloneService.getGameById(gameId).orElse(null);
        assertNotNull(game);
        assertFalse(game.getWin());
        this.gameAloneService.deleteGame(game);
    }



    
}
