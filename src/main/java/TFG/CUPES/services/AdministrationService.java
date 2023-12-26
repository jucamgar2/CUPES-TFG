package TFG.CUPES.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TFG.CUPES.components.GameUtils;
import TFG.CUPES.entities.ImageForm;
import TFG.CUPES.entities.Position;
import net.coobird.thumbnailator.Thumbnails;

@Service
public class AdministrationService {
    
    @Autowired
    private PositionService positionService;

    GameUtils gameUtils = new GameUtils();
    
    public Boolean imageHasMoreThanFiveValidPositions(String image) throws IOException{
        List<Position> positions = this.positionService.findAll();
        Boolean res = false;
        Integer count = 0;
        for(Position p : positions){
            if(gameUtils.checkImageHasMoreThan1Color(image, p)){
                count++;
            }
            if(count>5){
                res = true;
                return res;
            }
        }
        return res;
    }

    public void resizeInputImage(ImageForm imageForm) throws IOException{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(imageForm.getFile().getInputStream())
        .size(2000, 2000)
        .outputFormat("JPEG")
        .outputQuality(1)
        .toOutputStream(outputStream);
        byte[] data = outputStream.toByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        BufferedImage img =  ImageIO.read(inputStream);
        ImageIO.write(img, "jpg", new File("src/main/resources/static/images/Logo/" + imageForm.getName().toLowerCase().strip() + ".jpg"));
        
       
    }
}
