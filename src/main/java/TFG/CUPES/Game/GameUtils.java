package TFG.CUPES.Game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

    public Position randomImagePortion(String imageSelected,List<Position> gamePositions,List<Position> positions) {
        Random rand = new Random();
        List<Position> elegiblePositions = positions.stream().filter(x->!gamePositions.contains(x)).toList();
        Position newPosition = elegiblePositions.get(rand.nextInt(elegiblePositions.size()));
        return newPosition;
    }

    public Boolean checkImageHasMoreThan1Color(String imageSelected,Position position) throws IOException {
        ClassPathResource resource = new ClassPathResource("static"+imageSelected);
        BufferedImage image = ImageIO.read(resource.getInputStream());
        int width = 500;
        int height = 500;
        int totalPixels = width * height;
        Map<Integer, Integer> colorCounts = new HashMap<>();
        for (int i = position.getX()*4; i < position.getX()*4 + width && i < image.getWidth(); i++) {
            for (int j = position.getY()*4; j < position.getY()*4 + height && j < image.getHeight(); j++) {
                int pixelColor = image.getRGB(i, j);
                colorCounts.put(pixelColor, colorCounts.getOrDefault(pixelColor, 0) + 1);
            }
        }
        int maxCount = 0;
        for (Map.Entry<Integer, Integer> entry : colorCounts.entrySet()) {
            int count = entry.getValue();
            if (count > maxCount) {
                maxCount = count;
            }
        }
        double percentage = (double) maxCount / totalPixels;
        return percentage < 0.90;
    }

    public String generateImageStyle(String imageSelected,Position position) {
        return "backgroud-color: white;background-image: url('" + imageSelected + "'); width: " + 500+"px; height: " + 500 + "px; background-position: -" + position.getX()*4 + "px -" + position.getY()*4 + "px;";
    }

    public ModelAndView expelPlayer(){
        ModelAndView res  = new ModelAndView("redirect:/");
        res.addObject("errormsg", "Estas intentando acceder a una partida que no existe o que ya ha finalizado");
        return res;
    }

    public String generateImageStyle(List<Position> positions, List<Position> gamePositions) {
        String res = "";
        for(Position p : positions){
            if(gamePositions.contains(p)){
            }else{
                res +="<div style='width: 125px;height: 125px;position: absolute;background-color: red; top:" +p.getY()+"px;left: "+(p.getX()+125)+"px;'><img src='/images/int.png'></img></div>";
            }
        }
        return res;
    }
}
