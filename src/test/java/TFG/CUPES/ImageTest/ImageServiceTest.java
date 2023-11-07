package TFG.CUPES.ImageTest;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import TFG.CUPES.Image.Image;
import TFG.CUPES.Image.ImageService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ImageServiceTest {

    @Autowired
    ImageService imageService;

    @Test
    public void getRandomLogoTest(){
        Image i = this.imageService.getRandomLogo();
        assert(i!=null);
        assert(i.getId() != null);
        assert(i.getName() != null);
        assert(i.getResourceName()!=null);
    }

    @Test
    public void getAllLogosTest(){
        List<Image> logos = this.imageService.getAllLogos();
        assert(!logos.isEmpty());
        assert(logos.size()>100);
    }

    @Test
    public void getLogoByIdTest(){
        Image i = this.imageService.getLogoById(55);
        assert(i!=null);
        assert(i.getId() ==55);
        assertEquals(i.getName(), "Olympique de Lyon");
        assertEquals(i.getResourceName(), "OL");
        assertEquals(i.getImageType(), "Logo");
    }
    
}
