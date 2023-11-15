package TFG.CUPES.Admin;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import TFG.CUPES.Game.GameUtils;
import TFG.CUPES.Game.Position;
import TFG.CUPES.Game.PositionService;
import TFG.CUPES.Image.ImageForm;
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

    public void resizeImputImage(ImageForm imageForm) {
        try {
            // Tu código para obtener originalImage

            // Redimensionar la imagen
            //BufferedImage resizedImage = Thumbnails.of(imageForm.getFile().getInputStream()).size(2000, 2000).asBufferedImage();

            // Ruta de la imagen redimensionada
            String destinationPath = "src/main/resources/static/images/Logo/" + imageForm.getName().toLowerCase().strip() + ".jpg";

            // Crear directorios si no existen
            Files.createDirectories(Path.of(destinationPath).getParent());

            // Guardar la imagen redimensionada usando Files.copy
            //byte[] r = bufferedImageToByteArray(resizedImage);
            //System.out.println("Bytes"+r);

            Files.copy(imageForm.getFile().getInputStream(), Path.of(destinationPath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] bufferedImageToByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", os);
        return os.toByteArray();
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
