package TFG.CUPES.Game;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PositionService {
    
    private PositionRepository positionController;

    public PositionService(PositionRepository positionController){
        this.positionController = positionController;
    }

    public Position findPositionByXAndY(Integer x, Integer y){
        return this.positionController.findPositionByXAndY(x, y);
    }

    public List<Position> findAll(){
        return (List<Position>) this.positionController.findAll();
    }
}
