package TFG.CUPES.PlayerTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayerControllerTest2 {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void saveNewPlayerTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/players/new")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("username", "nuevoUsuario")
                .param("mail", "nuevo@usuario.com")
                .param("password", "contraseña")
                .param("name", "nuevoNombre")
                .param("birthDate", "01/01/1999"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
}
