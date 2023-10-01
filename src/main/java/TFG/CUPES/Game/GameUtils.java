package TFG.CUPES.Game;

import java.util.HashSet;
import java.util.List;
import java.util.Random;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;


import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.ModelAndView;

public class GameUtils {

    public List<Integer> getFootballImagePosition(){
        return List.of(0,500,1000,1500);
    }

    public Position randomImagePortion(String imageSelected,Position position) {
        Random rand = new Random();
        Integer newX = position.getX();
        Integer newY = position.getY();
        if (newX == null || newX == position.getX()) {
            newX = getFootballImagePosition().get(rand.nextInt(getFootballImagePosition().size()));
        }
        if (newY == null || newY == position.getY()) {
            newY = getFootballImagePosition().get(rand.nextInt(getFootballImagePosition().size()));
        }
        return new Position(newX, newY);
    }

    public Boolean checkImageHasMoreThan1Color(String imageSelected,Position position) throws IOException {
        ClassPathResource resource = new ClassPathResource("static/"+imageSelected);
        BufferedImage image = ImageIO.read(resource.getInputStream());
        Boolean res = false;
        int width = 500;
        int height = 500;
        HashSet<Integer> uniqueColors = new HashSet<>();
        for (int i = position.getX(); i < position.getX() + width && i < image.getWidth(); i++) {
            for (int j = position.getY(); j < position.getY() + height && j < image.getHeight(); j++) {
                int pixelColor = image.getRGB(i, j);
                uniqueColors.add(pixelColor);
                if (uniqueColors.size() > 1) {
                    res = true;
                    return res;
                }
            }
        }
       return res;
    }

    public String generateImageStyle(String imageSelected,Position position) {
        return "backgroud-color: white;background-image: url('" + imageSelected + "'); width: " + 500+"px; height: " + 500 + "px; background-position: -" + position.getX() + "px -" + position.getY() + "px;";
    }

    public ModelAndView expelPlayer(){
        ModelAndView res  = new ModelAndView("redirect:/");
        res.addObject("errormsg", "Estas intentando acceder a una partida que no existe o que ya ha finalizado");
        return res;
    }
}
