package TFG.CUPES.Game;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameAloneRepository extends CrudRepository<GameAlone, Integer> {

    @Query("SELECT g FROM GameAlone g WHERE g.token = ?1 AND g.id = ?2")
    Optional<GameAlone> getGameByTokenAndId(String token, Integer id);
}
