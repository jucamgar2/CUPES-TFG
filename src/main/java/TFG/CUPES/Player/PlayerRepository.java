package TFG.CUPES.Player;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PlayerRepository extends CrudRepository<Player,String>{

    @Query("select p from Player p where p.username = ?1")
    Player findByUsername(String name);
    
}
