package TFG.CUPES.LocalGameTest;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import TFG.CUPES.entities.GameAlone;
import TFG.CUPES.entities.LocalGame;
import TFG.CUPES.services.LocalGameService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LocalGameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    LocalGameService localGameService;

    @Test
    public void startNewGameTest() throws Exception {
        mockMvc.perform(get("/game/localGame/new"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("localGame"))
        .andExpect(view().name("game/newLocalGame"));
    }

    @Test
    public void createNewGameSuccessTest() throws Exception{
        mockMvc.perform(post("/game/localGame/new")
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("player1Name", "Guaje")
        .param("player2Name", "Antonio"))
        .andExpect(status().is3xxRedirection())
        .andExpect(model().attributeDoesNotExist("errors"));
    }

    @Test
    public void createNewGameEmptyTest() throws Exception{
        mockMvc.perform(post("/game/localGame/new")
        .with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("errors"));
    }

    @Test
    public void createNewGameEmptyNamesTest() throws Exception{
        mockMvc.perform(post("/game/localGame/new")
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("player1Name", "")
        .param("player2Name", ""))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("errors"));
    }

    @Test
    public void createNewGameSameNamesTest() throws Exception{
        mockMvc.perform(post("/game/localGame/new")
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("player1Name", "Messi")
        .param("player2Name", "Messi"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("errors"));
    }

    @Test
    public void playGameTest() throws Exception{
        MvcResult mvcResult = mockMvc.perform(post("/game/localGame/new")
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("player1Name", "Guaje")
        .param("player2Name", "Antonio"))
        .andExpect(status().is3xxRedirection())
        .andExpect(model().attributeDoesNotExist("errors"))
        .andReturn();
        String redirectedUrl = mvcResult.getResponse().getHeader("Location");
        assertNotNull(redirectedUrl);
        assertTrue(redirectedUrl.startsWith("/game/localGame/play/"));
        String[] urlParts = redirectedUrl.split("/");
        String part = urlParts[urlParts.length-1];
        Integer gameId = Integer.parseInt(String.valueOf(part.charAt(0)));
        LocalGame game = localGameService.getLocalGameById(gameId).orElse(null);
        assertNotNull(game);
        mockMvc.perform(get("/game/localGame/play/"+gameId+"?token="+game.getToken()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playLocalGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"));
        mockMvc.perform(post("/game/localGame/play/"+gameId+"?token="+game.getToken())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", game.getPlayer1Image().getName()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/localGame/play/"+gameId+"?token="+game.getToken()));
        game = localGameService.getLocalGameById(gameId).orElse(null);
        assert(game.getActualPlayer().equals(game.getPlayer2Name()));
        mockMvc.perform(get("/game/localGame/play/"+gameId+"?token="+game.getToken()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playLocalGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"));
        mockMvc.perform(post("/game/localGame/play/"+gameId+"?token="+game.getToken())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", game.getPlayer2Image().getName()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/localGame/res/"+gameId+"?token="+game.getToken()));
        game = localGameService.getLocalGameById(gameId).orElse(null);
        assert(game.getWinner()!=null);
        mockMvc.perform(get("/game/localGame/res/"+gameId+"?token="+game.getToken()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/resLocalGame"));
    }

    @Test
    public void playGameTestPlayer1Fails() throws Exception{
        MvcResult mvcResult = mockMvc.perform(post("/game/localGame/new")
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("player1Name", "Guaje")
        .param("player2Name", "Antonio"))
        .andExpect(status().is3xxRedirection())
        .andExpect(model().attributeDoesNotExist("errors"))
        .andReturn();
        String redirectedUrl = mvcResult.getResponse().getHeader("Location");
        assertNotNull(redirectedUrl);
        assertTrue(redirectedUrl.startsWith("/game/localGame/play/"));
        String[] urlParts = redirectedUrl.split("/");
        String part = urlParts[urlParts.length-1];
        Integer gameId = Integer.parseInt(String.valueOf(part.charAt(0)));
        LocalGame game = localGameService.getLocalGameById(gameId).orElse(null);
        assertNotNull(game);
        mockMvc.perform(get("/game/localGame/play/"+gameId+"?token="+game.getToken()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playLocalGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"));
        mockMvc.perform(post("/game/localGame/play/"+gameId+"?token="+game.getToken())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", "x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/localGame/play/"+gameId+"?token="+game.getToken()));
        mockMvc.perform(get("/game/localGame/play/"+gameId+"?token="+game.getToken()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playLocalGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"));
        mockMvc.perform(post("/game/localGame/play/"+gameId+"?token="+game.getToken())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", "x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/localGame/play/"+gameId+"?token="+game.getToken()));
        mockMvc.perform(get("/game/localGame/play/"+gameId+"?token="+game.getToken()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playLocalGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"));
        mockMvc.perform(post("/game/localGame/play/"+gameId+"?token="+game.getToken())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", "x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/localGame/play/"+gameId+"?token="+game.getToken()));
        mockMvc.perform(get("/game/localGame/play/"+gameId+"?token="+game.getToken()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playLocalGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"));
        mockMvc.perform(post("/game/localGame/play/"+gameId+"?token="+game.getToken())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", "x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/localGame/play/"+gameId+"?token="+game.getToken()));
        mockMvc.perform(get("/game/localGame/play/"+gameId+"?token="+game.getToken()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/localGame/play/"+gameId+"?token="+game.getToken()));
        game = localGameService.getLocalGameById(gameId).orElse(null);
        assert(game.getWinner()==null);
        assertEquals(game.getActualPlayer(), game.getPlayer2Name());
        mockMvc.perform(post("/game/localGame/play/"+gameId+"?token="+game.getToken())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", game.getPlayer2Image().getName()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/localGame/res/"+gameId+"?token="+game.getToken()));
        mockMvc.perform(get("/game/localGame/play/"+gameId+"?token="+game.getToken()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/localGame/res/"+gameId+"?token="+game.getToken()));
        game = localGameService.getLocalGameById(gameId).orElse(null);
        assert(game.getWinner()!=null);
        mockMvc.perform(get("/game/localGame/res/"+gameId+"?token="+game.getToken()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/resLocalGame"));
        game = localGameService.getLocalGameById(gameId).orElse(null);
        assert(game.getWinner().equals(game.getPlayer2Name()));
    }

    @Test
    public void playGamePlayer2Fails() throws Exception{
        MvcResult mvcResult = mockMvc.perform(post("/game/localGame/new")
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("player1Name", "Guaje")
        .param("player2Name", "Antonio"))
        .andExpect(status().is3xxRedirection())
        .andExpect(model().attributeDoesNotExist("errors"))
        .andReturn();
        String redirectedUrl = mvcResult.getResponse().getHeader("Location");
        assertNotNull(redirectedUrl);
        assertTrue(redirectedUrl.startsWith("/game/localGame/play/"));
        String[] urlParts = redirectedUrl.split("/");
        String part = urlParts[urlParts.length-1];
        Integer gameId = Integer.parseInt(String.valueOf(part.charAt(0)));
        LocalGame game = localGameService.getLocalGameById(gameId).orElse(null);
        assertNotNull(game);
        mockMvc.perform(get("/game/localGame/play/"+gameId+"?token="+game.getToken()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playLocalGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"));
        mockMvc.perform(post("/game/localGame/play/"+gameId+"?token="+game.getToken())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", game.getPlayer1Image().getName()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/localGame/play/"+gameId+"?token="+game.getToken()));
        game = localGameService.getLocalGameById(gameId).orElse(null);
        assert(game.getActualPlayer().equals(game.getPlayer2Name()));
        mockMvc.perform(get("/game/localGame/play/"+gameId+"?token="+game.getToken()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playLocalGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"));
        mockMvc.perform(post("/game/localGame/play/"+gameId+"?token="+game.getToken())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", "x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/localGame/res/"+gameId+"?token="+game.getToken()));
        mockMvc.perform(get("/game/localGame/play/"+gameId+"?token="+game.getToken()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playLocalGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"));
        mockMvc.perform(post("/game/localGame/play/"+gameId+"?token="+game.getToken())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", "x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/localGame/res/"+gameId+"?token="+game.getToken()));
        mockMvc.perform(get("/game/localGame/play/"+gameId+"?token="+game.getToken()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playLocalGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"));
        mockMvc.perform(post("/game/localGame/play/"+gameId+"?token="+game.getToken())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", "x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/localGame/res/"+gameId+"?token="+game.getToken()));
        mockMvc.perform(get("/game/localGame/play/"+gameId+"?token="+game.getToken()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playLocalGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"));
        mockMvc.perform(post("/game/localGame/play/"+gameId+"?token="+game.getToken())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", "x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/localGame/res/"+gameId+"?token="+game.getToken()));
        mockMvc.perform(get("/game/localGame/play/"+gameId+"?token="+game.getToken()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/localGame/res/"+gameId+"?token="+game.getToken()));
        mockMvc.perform(get("/game/localGame/res/"+gameId+"?token="+game.getToken()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/resLocalGame"));
        game = localGameService.getLocalGameById(gameId).orElse(null);
        assert(game.getWinner().equals(game.getPlayer1Name()));
    }

    @Test
    public void playGameDrawTest() throws Exception{
        MvcResult mvcResult = mockMvc.perform(post("/game/localGame/new")
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("player1Name", "Guaje")
        .param("player2Name", "Antonio"))
        .andExpect(status().is3xxRedirection())
        .andExpect(model().attributeDoesNotExist("errors"))
        .andReturn();
        String redirectedUrl = mvcResult.getResponse().getHeader("Location");
        assertNotNull(redirectedUrl);
        assertTrue(redirectedUrl.startsWith("/game/localGame/play/"));
        String[] urlParts = redirectedUrl.split("/");
        String part = urlParts[urlParts.length-1];
        Integer gameId = Integer.parseInt(String.valueOf(part.charAt(0)));
        LocalGame game = localGameService.getLocalGameById(gameId).orElse(null);
        assertNotNull(game);
        mockMvc.perform(get("/game/localGame/play/"+gameId+"?token="+game.getToken()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playLocalGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"));
        mockMvc.perform(post("/game/localGame/play/"+gameId+"?token="+game.getToken())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", "x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/localGame/play/"+gameId+"?token="+game.getToken()));
        mockMvc.perform(get("/game/localGame/play/"+gameId+"?token="+game.getToken()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playLocalGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"));
        mockMvc.perform(post("/game/localGame/play/"+gameId+"?token="+game.getToken())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", "x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/localGame/play/"+gameId+"?token="+game.getToken()));
        mockMvc.perform(get("/game/localGame/play/"+gameId+"?token="+game.getToken()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playLocalGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"));
        mockMvc.perform(post("/game/localGame/play/"+gameId+"?token="+game.getToken())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", "x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/localGame/play/"+gameId+"?token="+game.getToken()));
        mockMvc.perform(get("/game/localGame/play/"+gameId+"?token="+game.getToken()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playLocalGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"));
        mockMvc.perform(post("/game/localGame/play/"+gameId+"?token="+game.getToken())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", "x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/localGame/play/"+gameId+"?token="+game.getToken()));
        mockMvc.perform(get("/game/localGame/play/"+gameId+"?token="+game.getToken()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/localGame/play/"+gameId+"?token="+game.getToken()));
        game = localGameService.getLocalGameById(gameId).orElse(null);
        assert(game.getActualPlayer().equals(game.getPlayer2Name()));
        mockMvc.perform(get("/game/localGame/play/"+gameId+"?token="+game.getToken()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playLocalGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"));
        mockMvc.perform(post("/game/localGame/play/"+gameId+"?token="+game.getToken())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", "x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/localGame/res/"+gameId+"?token="+game.getToken()));
        mockMvc.perform(get("/game/localGame/play/"+gameId+"?token="+game.getToken()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playLocalGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"));
        mockMvc.perform(post("/game/localGame/play/"+gameId+"?token="+game.getToken())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", "x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/localGame/res/"+gameId+"?token="+game.getToken()));
        mockMvc.perform(get("/game/localGame/play/"+gameId+"?token="+game.getToken()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playLocalGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"));
        mockMvc.perform(post("/game/localGame/play/"+gameId+"?token="+game.getToken())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", "x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/localGame/res/"+gameId+"?token="+game.getToken()));
        mockMvc.perform(get("/game/localGame/play/"+gameId+"?token="+game.getToken()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/playLocalGame"))
        .andExpect(model().attributeExists("game"))
        .andExpect(model().attributeExists("logo"));
        mockMvc.perform(post("/game/localGame/play/"+gameId+"?token="+game.getToken())
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("name", "x"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/localGame/res/"+gameId+"?token="+game.getToken()));
        mockMvc.perform(get("/game/localGame/play/"+gameId+"?token="+game.getToken()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/localGame/res/"+gameId+"?token="+game.getToken()));
        mockMvc.perform(get("/game/localGame/res/"+gameId+"?token="+game.getToken()))
        .andExpect(status().isOk())
        .andExpect(view().name("game/resLocalGame"));
        game = localGameService.getLocalGameById(gameId).orElse(null);
        assert(game.getWinner().equals("Empate"));
    }

    @Test
    public void playGameNotExistsTest() throws Exception{
        mockMvc.perform(get("/game/localGame/play/0"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("game/selectMode"))
        .andExpect(model().attributeExists("errors"));
    }

    @Test
    public void playGameNotExistsTest2() throws Exception{
        mockMvc.perform(get("/game/localGame/play/0?token=0"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("game/selectMode"))
        .andExpect(model().attributeExists("errors"));
    }

    @Test
    public void playGameNotExistsTest3() throws Exception{
        mockMvc.perform(post("/game/localGame/play/1986?token=0")
        .with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("game/selectMode"))
        .andExpect(model().attributeExists("errors"));
    }

    @Test
    public void playGameNotExistsTest4() throws Exception{
        mockMvc.perform(post("/game/localGame/play/1986")
        .with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("game/selectMode"))
        .andExpect(model().attributeExists("errors"));
    }

    @Test
    public void resLocalGameGameNotExistsTest() throws Exception{
        mockMvc.perform(get("/game/localGame/res/0"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("game/selectMode"))
        .andExpect(model().attributeExists("errors"));
    }

    @Test
    public void resGameNotExistsTest2() throws Exception{
        mockMvc.perform(get("/game/localGame/res/0?token=0"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("game/selectMode"))
        .andExpect(model().attributeExists("errors"));
    }

    @Test
    public void resGameNotFinishedTest() throws Exception{
        MvcResult mvcResult = mockMvc.perform(post("/game/localGame/new")
        .with(SecurityMockMvcRequestPostProcessors.csrf())
        .param("player1Name", "Guaje")
        .param("player2Name", "Antonio"))
        .andExpect(status().is3xxRedirection())
        .andExpect(model().attributeDoesNotExist("errors"))
        .andReturn();
        String redirectedUrl = mvcResult.getResponse().getHeader("Location");
        assertNotNull(redirectedUrl);
        assertTrue(redirectedUrl.startsWith("/game/localGame/play/"));
        String[] urlParts = redirectedUrl.split("/");
        String part = urlParts[urlParts.length-1];
        Integer gameId = Integer.parseInt(String.valueOf(part.charAt(0)));
        LocalGame game = localGameService.getLocalGameById(gameId).orElse(null);
        assertNotNull(game);
        mockMvc.perform(get("/game/localGame/res/"+gameId+"?token="+game.getToken()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/game/localGame/play/"+gameId+"?token="+game.getToken()));
    }


    
}
