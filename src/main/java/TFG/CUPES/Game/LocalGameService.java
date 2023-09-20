package TFG.CUPES.Game;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocalGameService {

    LocalGameRepository localGameRepository;

    @Autowired
    LocalGameService(LocalGameRepository localGameRepository){
        this.localGameRepository = localGameRepository;
    }

    public void save(LocalGame localGame){
        this.localGameRepository.save(localGame);
    }

    public Optional<LocalGame> getLocalGameById(Integer id){
        return this.localGameRepository.findById(id);
    }
}
