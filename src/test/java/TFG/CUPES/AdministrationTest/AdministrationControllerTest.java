package TFG.CUPES.AdministrationTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AdministrationControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username="admin",authorities={"admin"})
    public void administrationTest() throws Exception{
        this.mockMvc.perform(get("/administration"))
        .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username="admin",authorities={"admin"})
    public void newImageTest() throws Exception{
        this.mockMvc.perform(get("/administration/images/new"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("imageForm"));
    }

    @Test
    @WithMockUser(username="admin",authorities={"admin"})
    public void newImageWithNoFileTest() throws Exception{
        MockMultipartFile emptyFile = new MockMultipartFile("file", "test.txt", "text/plain", new byte[0]);

        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/administration/images/new")
                .file(emptyFile)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("name", "nuevoNombre"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("errors"))
                .andExpect(model().attribute("errors", List.of("No se ha seleccionado ninguna imagen")));
    }

}
