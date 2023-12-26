package TFG.CUPES.StatisticsTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class StatisticsControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getStatisticsTest() throws Exception{
        this.mockMvc.perform(get("/statistics"))
        .andExpect(status().isOk());
    }
}
