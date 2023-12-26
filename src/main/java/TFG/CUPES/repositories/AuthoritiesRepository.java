package TFG.CUPES.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import TFG.CUPES.entities.Authorities;

@Repository
public interface AuthoritiesRepository extends CrudRepository<Authorities, Integer>{
    
    @Query("SELECT MAX(a.id) FROM Authorities a")
    Integer findMaxId();

    @Query("SELECT a FROM Authorities a WHERE a.player.username = ?1")
    Authorities findByUsername(String username);
}
