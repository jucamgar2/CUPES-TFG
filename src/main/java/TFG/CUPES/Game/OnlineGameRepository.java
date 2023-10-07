package TFG.CUPES.Game;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OnlineGameRepository extends CrudRepository<OnlineGame, Integer>{
    
    @Query("SELECT g FROM OnlineGame g WHERE g.player2 IS NULL")
    List<OnlineGame> findNotStartedGames();
}
