package TFG.CUPES.Player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    @Autowired
    PlayerRepository playerRepo;

    public void save(Player p){
        this.playerRepo.save(p);
    }
}
