package TFG.CUPES.Game;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OnlineGameService {
    
    private OnlineGameRepository onlineGameRepository;

    @Autowired
    private OnlineGameService(OnlineGameRepository onlineGameRepository){
        this.onlineGameRepository = onlineGameRepository;
    }

    public void save(OnlineGame onlineGame){
        this.onlineGameRepository.save(onlineGame);
    }

    public Optional<OnlineGame> getOnlineGameByid(Integer gameId){
        return this.onlineGameRepository.findById(gameId);
    }

    public List<OnlineGame> getNotStartedGames(){
        return this.onlineGameRepository.findNotStartedGames();
    }

    public void delete(OnlineGame onlineGame){
        this.onlineGameRepository.delete(onlineGame);
    }

}