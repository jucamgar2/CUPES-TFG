package TFG.CUPES.PlayerTest;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import TFG.CUPES.controllers.PlayerController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayerControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired 
    private PlayerController playerController;

    @Test
    public void createPlayerTest() throws Exception{
        this.mockMvc.perform(get("/players/new"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("player"));  
    }

    @Test
    public void saveNewPlayerTest() throws Exception{
        mockMvc.perform(post("/players/new")
            .with(SecurityMockMvcRequestPostProcessors.csrf())
            .param("username", "nuevoUsuario")
            .param("mail", "nuevo@usuario.com")
            .param("password", "contraseña")
            .param("name", "nuevoNombre")
            .param("birthDate", "01/01/1999"))
            .andExpect(status().isOk());   
    }
}
