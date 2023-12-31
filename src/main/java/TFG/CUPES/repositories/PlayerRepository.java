package TFG.CUPES.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import TFG.CUPES.entities.GameAlone;
import TFG.CUPES.entities.Player;
@Repository
public interface PlayerRepository extends CrudRepository<Player,String>{

    @Query("select p from Player p where p.username = ?1")
    Player findByUsername(String name);
    
    @Query("select p from Player p where p.mail = ?1")
    Player findByMail(String mail);

    @Query("select p from Player p")
    Page<Player> findAll(PageRequest of);

    @Query("SELECT g FROM GameAlone g WHERE g.player = ?1")
    List<GameAlone> findGamesByPlayer(Player player);
}
