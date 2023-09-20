package TFG.CUPES.Game;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface LocalGameRepository extends CrudRepository<LocalGame,Integer>{
    
}
