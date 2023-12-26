package TFG.CUPES.repositories;


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

    
}
