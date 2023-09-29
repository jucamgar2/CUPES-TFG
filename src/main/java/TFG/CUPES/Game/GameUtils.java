package TFG.CUPES.Game;

import java.time.Duration;
import java.util.List;
import java.util.Random;

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

    public String generateImageStyle(String imageSelected,Position position) {
        return "backgroud-color: white;background-image: url('" + imageSelected + "'); width: " + 500+"px; height: " + 500 + "px; background-position: -" + position.getX() + "px -" + position.getY() + "px;";
    }

    public ModelAndView expelPlayer(){
        ModelAndView res  = new ModelAndView("redirect:/");
        res.addObject("errormsg", "Estas intentando acceder a una partida que no existe o que ya ha finalizado");
        return res;
    }
}
