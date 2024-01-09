package TFG.CUPES.GameAloneTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
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

    
}
