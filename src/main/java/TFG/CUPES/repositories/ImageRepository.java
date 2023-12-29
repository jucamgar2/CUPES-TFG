package TFG.CUPES.repositories;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import TFG.CUPES.entities.Image;

@Repository
public interface ImageRepository extends CrudRepository<Image,Integer>{
    
    @Query("SELECT MAX(i.id) FROM Image i")
    int maxId();

    @Query("SELECT i FROM Image i")
    Page<Image> findAll(PageRequest of);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.selected = ?1")
    Integer findImageByGameAlone(Image image);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.selected = ?1 AND g.win")
    Integer findSuccesFromImage(Image image);

    @Query("SELECT i FROM Image i WHERE i.enabled")
    List<Image> findAllEnabled();
    
}
