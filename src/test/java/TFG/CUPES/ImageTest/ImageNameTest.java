package TFG.CUPES.ImageTest;

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
public class ImageNameTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAutocompleteWordsTest() throws Exception{
        this.mockMvc.perform(get("/autocomplete/logos"))
         .andExpect(status().isOk());
    }

    @Test
    public void getAutocompletePageTest() throws Exception{
        this.mockMvc.perform(get("/autocomplete"))
         .andExpect(status().isOk());
    }
    
}
