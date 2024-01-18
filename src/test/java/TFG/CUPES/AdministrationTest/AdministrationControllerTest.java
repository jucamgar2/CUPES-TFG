package TFG.CUPES.AdministrationTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import TFG.CUPES.entities.Image;
import TFG.CUPES.entities.Player;
import TFG.CUPES.services.ImageService;
import TFG.CUPES.services.PlayerService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AdministrationControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ImageService imageService;

    @Autowired
    private PlayerService playerService;

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

    @Test
    @WithMockUser(username="admin",authorities={"admin"})
    public void newImageWithNoNameTest() throws Exception{
        MockMultipartFile file = new MockMultipartFile("file", "test.png", "text/plain", "test".getBytes());

        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/administration/images/new")
                .file(file)
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("errors"))
                .andExpect(model().attribute("errors", List.of("No se ha introducido ningún nombre")));
    }

    @Test
    @WithMockUser(username="admin",authorities={"admin"})
    public void newImageWithEmptyNameTest() throws Exception{
        MockMultipartFile file = new MockMultipartFile("file", "test.png", "text/plain", "test".getBytes());

        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/administration/images/new")
                .file(file)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("name", ""))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("errors"))
                .andExpect(model().attribute("errors", List.of("No se ha introducido ningún nombre")));
    }


    @Test
    @WithMockUser(username="admin",authorities={"admin"})
    public void newImageWithTxtFileTest() throws Exception{
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes());

        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/administration/images/new")
                .file(file)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("name", "Nombre"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("errors"))
                .andExpect(model().attribute("errors", List.of("El archivo seleccionado no es una imagen o no es un archivo png o jpg")));
    }

    @Test
    @WithMockUser(username="admin",authorities={"admin"})
    public void newImageWithFileNoNamedTest() throws Exception{
        MockMultipartFile file = new MockMultipartFile("file", "", "text/plain", "test".getBytes());

        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/administration/images/new")
                .file(file)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("name", "Nombre"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("errors"))
                .andExpect(model().attribute("errors", List.of("El archivo seleccionado no es una imagen o no es un archivo png o jpg")));
    }

    @Test
    @WithMockUser(username="admin",authorities={"admin"})
    public void newImagePNGTest() throws Exception{
        ClassPathResource resource = new ClassPathResource("static/images/Logo/OL.jpg");
        MockMultipartFile file = new MockMultipartFile("file", "image.png", "image/png", resource.getInputStream());
        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/administration/images/new")
                .file(file)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("name", "Nombre"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeDoesNotExist("errors"));
    }

    @Test
    @WithMockUser(username="admin",authorities={"admin"})
    public void imageListTest() throws Exception{
        this.mockMvc.perform(get("/administration/images"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("images"));
    }

    @Test
    @WithMockUser(username="admin",authorities={"admin"})
    public void imageListSearchTest() throws Exception{
        this.mockMvc.perform(get("/administration/images?name=a"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("images"));
    }

    @Test
    @WithMockUser(username="admin",authorities={"admin"})
    public void viewImageTest() throws Exception{
        Image i = new Image();
        i.setId(1234);
        i.setCategory(1);
        i.setName("Nombre");
        i.setResourceName("Celta");
        i.setImageType("Logo");
        i.setEnabled(true);
        i.setHasYear(true);
        i.setHasInitials(true);
        i.setHasName(true);
        this.imageService.save(i);
        this.mockMvc.perform(get("/administration/images/view/1234"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("image"))
        .andExpect(model().attributeExists("positionStyle"));
    }

    @Test
    @WithMockUser(username="admin",authorities={"admin"})
    public void viewImageNotExistsTest() throws Exception{
        this.mockMvc.perform(get("/administration/images/view/8808"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/administration/images"));
    }

    @Test
    @WithMockUser(username="admin",authorities={"admin"})
    public void disableImageTest() throws Exception{
        Image i = new Image();
        i.setId(1235);
        i.setCategory(1);
        i.setName("Nombre");
        i.setResourceName("Celta");
        i.setImageType("Logo");
        i.setEnabled(true);
        i.setHasYear(true);
        i.setHasInitials(true);
        i.setHasName(true);
        this.imageService.save(i);
        this.mockMvc.perform(get("/administration/images/disable/1235"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/administration/images"));
        Image i2 = this.imageService.getLogoById(1235);
        assert(!i2.getEnabled());
    }

    @Test
    @WithMockUser(username="admin",authorities={"admin"})
    public void enableImageTest()throws Exception{
        Image i = new Image();
        i.setId(1236);
        i.setCategory(1);
        i.setName("Nombre");
        i.setResourceName("Celta");
        i.setImageType("Logo");
        i.setEnabled(false);
        i.setHasYear(true);
        i.setHasInitials(true);
        i.setHasName(true);
        this.imageService.save(i);
        this.mockMvc.perform(get("/administration/images/enable/1236"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/administration/images"));
        Image i2 = this.imageService.getLogoById(1236);
        assert(i2.getEnabled());
    }

    @Test
    @WithMockUser(username="admin",authorities={"admin"})
    public void disableImageNotExistsTest()throws Exception{
        this.mockMvc.perform(get("/administration/images/disable/8368"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/administration/images"));
        Image i = this.imageService.getLogoById(8368);
        assert(i==null);
    }

    @Test
    @WithMockUser(username="admin",authorities={"admin"})
    public void enableImageNotExistsTest()throws Exception{
        this.mockMvc.perform(get("/administration/images/enable/1385"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/administration/images"));
        Image i = this.imageService.getLogoById(1385);
        assert(i==null);
    }

    @Test
    @WithMockUser(username="admin",authorities={"admin"})
    public void exportImageDataTest() throws Exception{
        mockMvc.perform(get("/administration/images/exportData/1"))
        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin",authorities={"admin"})
    public void exportImageDataNotExistsTest() throws Exception{
        mockMvc.perform(get("/administration/images/exportData/1730"))
        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin",authorities={"admin"})
    public void exportUserDataTest() throws Exception{
        mockMvc.perform(get("/administration/players/exportData/Guaje"))
        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin",authorities={"admin"})
    public void exportUserDataNotExistsTest() throws Exception{
        mockMvc.perform(get("/administration/players/exportData/Guaje1323rsdf"))
        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin",authorities={"admin"})
    public void exportGeneralDataWithOutRangeTest() throws Exception{
        mockMvc.perform(post("/administration/images/statistics")
        .with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin",authorities={"admin"})
    public void viewImagesStatisticsTest() throws Exception{
        mockMvc.perform(get("/administration/images/statistics"))
        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin",authorities={"admin"})
    public void viewPlayersTest() throws Exception{
        mockMvc.perform(get("/administration/players"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("players"));
    }

    @Test
    @WithMockUser(username="admin",authorities={"admin"})
    public void viewPlayerWithSearchTest() throws Exception{
        mockMvc.perform(get("/administration/players?username=Guaje"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("players"));
    }

    @Test
    @WithMockUser(username="admin",authorities={"admin"})
    public void viewManualTest() throws Exception{
        mockMvc.perform(get("/administration/manual"))
        .andExpect(status().isOk())
        .andExpect(view().name("administration/manual"));
    }

    @Test
    @WithMockUser(username="admin",authorities={"admin"})
    public void disablePlayerTest() throws Exception{
        Player p = new Player();
        p.setUsername("Pepillo");
        p.setPassword("1234");
        p.setEnabled(true);
        this.playerService.save(p);
        this.mockMvc.perform(get("/administration/players/disable/Pepillo"))
        .andExpect(status().is3xxRedirection());
        Player p2 = this.playerService.getByUsername("Pepillo");
        assert(!p2.getEnabled());
    }
    @Test
    @WithMockUser(username="admin",authorities={"admin"})
    public void disablePlayerNotExistsTest() throws Exception{
        this.mockMvc.perform(get("/administration/players/disable/Pepillo3"))
        .andExpect(status().is3xxRedirection());
        Player p2 = this.playerService.getByUsername("Pepillo3");
        assert(p2==null);
    }

    @Test
    @WithMockUser(username="admin",authorities={"admin"})
    public void enablePlayerTest() throws Exception{
        Player p = new Player();
        p.setUsername("Pepillo2");
        p.setPassword("1234");
        p.setEnabled(false);
        this.playerService.save(p);
        this.mockMvc.perform(get("/administration/players/enable/Pepillo2"))
        .andExpect(status().is3xxRedirection());
        Player p2 = this.playerService.getByUsername("Pepillo2");
        assert(p2.getEnabled());
    }

    @Test
    @WithMockUser(username="admin",authorities={"admin"})
    public void enablePlayerNotExistsTest() throws Exception{
        this.mockMvc.perform(get("/administration/players/enable/Pepillo3"))
        .andExpect(status().is3xxRedirection());
        Player p2 = this.playerService.getByUsername("Pepillo3");
        assert(p2==null);
    }

}
