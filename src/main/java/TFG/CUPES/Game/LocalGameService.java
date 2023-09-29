package TFG.CUPES.Game;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocalGameService {

    LocalGameRepository localGameRepository;

    @Autowired
    LocalGameService(LocalGameRepository localGameRepository){
        this.localGameRepository = localGameRepository;
    }

    @Transactional
    public void save(LocalGame localGame){
        this.localGameRepository.save(localGame);
    }

    @Transactional(readOnly = true)
    public Optional<LocalGame> getLocalGameById(Integer id){
        return this.localGameRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<LocalGame> getLocalGameByTokenAndId(String token, Integer id){
        return this.localGameRepository.findLocalGameByTokenAndId(token, id);
    }


}
