package TFG.CUPES;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public class GeneralControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void welcomeTest() throws Exception{
        this.mockMvc.perform(get("/"))
        .andExpect(status().isOk());  
    }

    @Test
    public void manualTest() throws Exception{
        this.mockMvc.perform(get("/manual"))
        .andExpect(status().isOk());  
    }

    @Test
    public void manualGATest() throws Exception{
        this.mockMvc.perform(get("/manual/GA"))
        .andExpect(status().isOk());  
    }

    @Test
    public void manualLGTest() throws Exception{
        this.mockMvc.perform(get("/manual/LG"))
        .andExpect(status().isOk());  
    }

    @Test
    public void manualOGTest() throws Exception{
        this.mockMvc.perform(get("/manual/OG"))
        .andExpect(status().isOk());  
    }

    @Test
    public void handleErrorTest() throws Exception{
        this.mockMvc.perform(get("/error"))
        .andExpect(status().is3xxRedirection());  
    }
    
}
