package TFG.CUPES.ImageTest;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import TFG.CUPES.entities.Image;
import TFG.CUPES.services.ImageService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ImageServiceTest {

    @Autowired
    ImageService imageService;

    @Test
    public void getRandomLogoTest(){
        Image l = new Image();
        l.setId(1);
        l.setName("Olympique de Lyon");
        l.setResourceName("OL");
        l.setImageType("Logo");
        this.imageService.save(l);
        Image i = this.imageService.getRandomLogo();
        assert(i!=null);
        assert(i.getId() != null);
        assert(i.getName() != null);
        assert(i.getResourceName()!=null);
    }

    @Test
    public void getAllLogosTest(){
        Image l = new Image();
        l.setId(1);
        l.setName("Olympique de Lyon");
        l.setResourceName("OL");
        l.setImageType("Logo");
        this.imageService.save(l);
        List<Image> logos = this.imageService.getAllLogos();
        assert(!logos.isEmpty());
        assert(logos.size()>100);
    }

    @Test
    public void getLogoByIdTest(){
        Image l = new Image();
        l.setId(1);
        l.setName("Olympique de Lyon");
        l.setResourceName("OL");
        l.setImageType("Logo");
        this.imageService.save(l);
        Image i = this.imageService.getLogoById(1);
        assert(i!=null);
        assert(i.getId() ==1);
        assertEquals(i.getName(), "Olympique de Lyon");
        assertEquals(i.getResourceName(), "OL");
        assertEquals(i.getImageType(), "Logo");
    }
    
}
