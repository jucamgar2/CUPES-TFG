package TFG.CUPES.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import TFG.CUPES.entities.Position;

@Repository
public interface PositionRepository extends CrudRepository<Position, Integer>{

    @Query("SELECT p FROM Position p WHERE p.x = ?1 AND p.y = ?2")
    Position findPositionByXAndY(Integer x, Integer y);
    
}
