package TFG.CUPES.DTOdata;

import java.util.List;

import TFG.CUPES.entities.GameAlone;
import TFG.CUPES.entities.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ImageAndGamesDTO {
    private Image image;
    private List<GameAlone> games;
    
}
