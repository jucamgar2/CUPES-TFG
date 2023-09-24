package TFG.CUPES.Player;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    @Autowired
    PlayerRepository playerRepo;

    @Transactional
    public void save(Player p){
        this.playerRepo.save(p);
    }

    @Transactional(readOnly = true)
    public boolean exists(String username) {
        return this.playerRepo.existsById(username);
    }
}
