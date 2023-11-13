package TFG.CUPES.Image;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Image,Integer>{
    
    @Query("SELECT MAX(i.id) FROM Image i")
    int maxId();
}
