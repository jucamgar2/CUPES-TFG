package TFG.CUPES.PlayerTest;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import TFG.CUPES.entities.Player;
import TFG.CUPES.services.PlayerService;

import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PlayerControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlayerService playerService;

    @Test
    public void createPlayerTest() throws Exception{
        this.mockMvc.perform(get("/players/new"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("player"));  
    }

    @Test
    public void saveNewPlayerTestSuccess() throws Exception{
        mockMvc.perform(post("/players/new")
            .with(SecurityMockMvcRequestPostProcessors.csrf())
            .param("username", "nuevoUsuario")
            .param("password", "contraseña")
            .param("name", "nuevoNombre")
            .param("mail", "aaa@a.com"))
            .andExpect(status().is3xxRedirection());   
        Player p = playerService.getByUsername("nuevoUsuario");
        assertNotEquals(p, null);
    }

    @Test
    public void saveNewPlayerNotValid() throws Exception{
        mockMvc.perform(post("/players/new")
            .with(SecurityMockMvcRequestPostProcessors.csrf())
            .param("username", "nuevoUsuario")
            .param("password", "contraseña")
            .param("name", "nuevoNombre")
            .param("mail", ""))
            .andExpect(status().isOk());
    }

    @Test
    public void profileNotLogged() throws Exception{
        mockMvc.perform(get("/players/profile"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockUser(username="player1", authorities={"player"})
    public void profileLogged() throws Exception{
        Player p = new Player("player1","contraseña",true,"player1","a@a.com");
        playerService.save(p);
        mockMvc.perform(get("/players/profile"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/players/profile/player1"));
    }

    @Test
    @WithMockUser(username="player2", authorities={"player"})
    public void showProfile() throws Exception{
       mockMvc.perform(post("/players/new")
            .with(SecurityMockMvcRequestPostProcessors.csrf())
            .param("username", "player2")
            .param("password", "contraseña")
            .param("name", "nuevoNombre")
            .param("mail", "y@gmail.com"))
            .andExpect(status().is3xxRedirection());
        mockMvc.perform(get("/players/profile/player2"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="player1", authorities={"player"})
    public void editProfile() throws Exception{
        Player p = new Player("player1","contraseña",true,"player1","a@a.com");
        playerService.save(p);
        mockMvc.perform(get("/players/edit"))
            .andExpect(status().isOk());
            
        mockMvc.perform(post("/players/edit")
            .with(SecurityMockMvcRequestPostProcessors.csrf())
            .param("name", "nuevoNombre")
            .param("mail", "nuevoooo2@gmail.com"))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    public void editProfileNotLogged() throws Exception{
        mockMvc.perform(get("/players/edit"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("http://localhost/login"));
    }
}
