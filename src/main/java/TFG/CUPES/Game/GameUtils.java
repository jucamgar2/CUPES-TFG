package TFG.CUPES.Game;

import java.time.Duration;
import java.util.List;
import java.util.Random;

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

    public String checkWinner(LocalGame game) {
        String winner;
        if(game.getPlayer1Shifts()<game.getPlayer2Shifts()){
            winner = game.getPlayer1Name();
        }else if(game.getPlayer2Shifts()<game.getPlayer1Shifts()){
            winner = game.getPlayer2Name();
        }else{
            Duration player1Time = Duration.between(game.getPlayer1Start(),game.getPlayer1FInish());
            Duration player2Time = Duration.between(game.getPlayer2Start(),game.getPlayer2Finish());
            if(player1Time.compareTo(player2Time)<0){
                winner =game.getPlayer1Name();
            }else if(player2Time.compareTo(player1Time)<0){
                 winner = game.getPlayer2Name();
            }else{
                winner = "draw";
            }
        }
        return winner;
    }
     


}
