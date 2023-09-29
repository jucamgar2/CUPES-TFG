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
        int x = 999;
        int y = 999;
        while(position.getX()==null && position.getY()==null ||(position.getX()!=x && position.getY()!=y)){
            x = getFootballImagePosition().get(rand.nextInt(0, getFootballImagePosition().size()));
            y = getFootballImagePosition().get(rand.nextInt(0, getFootballImagePosition().size()));
            position.setX(x);
            position.setY(y);
        }
        return position;
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
