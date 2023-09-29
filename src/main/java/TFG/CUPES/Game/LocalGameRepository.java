package TFG.CUPES.Game;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface LocalGameRepository extends CrudRepository<LocalGame,Integer>{

    @Query("SELECT lg FROM LocalGame lg WHERE lg.token = :token AND lg.id = :id")
    Optional<LocalGame> findLocalGameByTokenAndId(String token, Integer id);
}
