package TFG.CUPES.Game;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameAloneService {

    private GameAloneRepository gameRepository;

    @Autowired
    public GameAloneService(GameAloneRepository gameRepo){
        this.gameRepository = gameRepo;
    }
    @Transactional
    public void saveGame(GameAlone g){
        this.gameRepository.save(g);
    }

    @Transactional
    public Optional<GameAlone> getGameById(Integer id){
        return this.gameRepository.findById(id);
    }
}
