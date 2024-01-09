package TFG.CUPES.ImageTest;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import TFG.CUPES.entities.ImageForm;

@ActiveProfiles("test")
public class ImageFormTest {

    @Test
    public void testImageForm(){
        ImageForm imageForm = new ImageForm();
        MultipartFile file = null;
        imageForm.setFile(file);
        assert(imageForm.getFile() == file);
        imageForm.setName("name");
        assert(imageForm.getName() == "name");
        imageForm.setResourceName("resourceName");
        assert(imageForm.getResourceName() == "resourceName");
        imageForm.setImageType("imageType");
        assert(imageForm.getImageType() == "imageType");
        imageForm.setCountry("country");
        assert(imageForm.getCountry() == "country");
        imageForm.setGenre("genre");
        assert(imageForm.getGenre() == "genre");
        imageForm.setCategory(1);
        assert(imageForm.getCategory() == 1);
        imageForm.setHasName(true);
        assert(imageForm.getHasName() == true);
        imageForm.setHasInitials(true);
        assert(imageForm.getHasInitials() == true);
        imageForm.setHasYear(true);
        assert(imageForm.getHasYear() == true);
        imageForm.setEnabled(true);
        assert(imageForm.getEnabled() == true);
        
        
    }
    
}
