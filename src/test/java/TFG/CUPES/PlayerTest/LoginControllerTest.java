package TFG.CUPES.PlayerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void loginTest() throws Exception{
        mockMvc.perform(get("/login"))
        .andExpect(status().isOk());  
    }

    @Test
    public void loginErrorTest() throws Exception{
        mockMvc.perform(get("/login?error=true"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(model().attributeExists("errors"));

         
    }
    
}
