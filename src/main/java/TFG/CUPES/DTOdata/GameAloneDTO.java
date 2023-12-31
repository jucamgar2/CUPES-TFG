package TFG.CUPES.DTOdata;

import java.util.List;

import TFG.CUPES.components.DateRangeForm;
import TFG.CUPES.entities.GameAlone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GameAloneDTO {
    private DateRangeForm dateRangeForm;
    private List<GameAlone> games;
}
