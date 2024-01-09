package TFG.CUPES.ImageTest;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;

import TFG.CUPES.entities.Image;
import TFG.CUPES.entities.Position;
import TFG.CUPES.services.ImageService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@ActiveProfiles("test")
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
        l.setEnabled(true);
        l.setCountry("Francia");
        l.setGenre("M");
        l.setCategory(1);
        l.setHasInitials(true);
        l.setHasName(false);
        l.setHasYear(false);
        this.imageService.save(l);
        Image i = this.imageService.getRandomLogo();
        assert(i!=null);
        assert(i.getId() != null);
        assert(i.getName() != null);
        assert(i.getResourceName()!=null);
        assert(i.getImageType()!=null);
        assert(i.getCountry()!=null);
        assert(i.getGenre()!=null);
        assert(i.getCategory()!=null);
        assert(i.getHasInitials()!=null);
        assert(i.getHasName()!=null);
        assert(i.getHasYear()!=null);
        assert(i.getEnabled()!=null);
    }

    @Test
    public void getAllLogosTest(){
        Image l = new Image();
        l.setId(1);
        l.setName("Olympique de Lyon");
        l.setResourceName("OL");
        l.setImageType("Logo");
        l.setEnabled(true);
        this.imageService.save(l);
        List<Image> logos = this.imageService.getAllLogos();
        assert(!logos.isEmpty());    }

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

    @Test
    public void testPositions(){
        Position p = new Position();
        p.setId(1);
        assert(p.getId()==1);
        p.setX(1);
        assert(p.getX()==1);
        p.setY(1);
        assert(p.getY()==1);
        assert(p.toString().equals("Position [x=1, y=1]"));
        assert(p.equals(p));
        assert(!p.equals(null));
        assert(!p.equals(new Object()));
        Position p2 = new Position();
        p2.setX(2);
        p2.setY(1);
        assert(!p.equals(p2));
    }
    
}
