package TFG.CUPES.DTOdata;

import java.util.List;

import TFG.CUPES.entities.GameAlone;
import TFG.CUPES.entities.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlayerAndGamesDTO {
    
    private Player player;

    private List<GameAlone> games;
    
}
