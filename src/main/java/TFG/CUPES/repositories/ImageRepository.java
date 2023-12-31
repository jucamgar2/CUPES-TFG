package TFG.CUPES.repositories;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import TFG.CUPES.entities.GameAlone;
import TFG.CUPES.entities.Image;

@Repository
public interface ImageRepository extends CrudRepository<Image,Integer>{
    
    @Query("SELECT MAX(i.id) FROM Image i")
    int maxId();

    @Query("SELECT i FROM Image i")
    Page<Image> findAll(PageRequest of);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.selected = ?1")
    Double findImageByGameAlone(Image image);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.selected = ?1 AND g.win")
    Double findSuccesFromImage(Image image);

    @Query("SELECT i FROM Image i WHERE i.enabled")
    List<Image> findAllEnabled();

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.selected.country = ?1 AND g.win")
    Double findSuccesFromCountryImage(String country);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.selected.country = ?1")
    Double findImageNumOfGamesFromCountry(String country);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.selected.country = ?1 AND g.win AND g.Shift = ?2")
    Double findImageNumOfSuccessFromCountryAndShifts(String country, Integer shifts);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.selected.genre = ?1")
    Double findNumOfGamesFromGenre(String genre);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.selected.genre = ?1 AND g.win")
    Double findNumOfSuccessFromGenre(String genre);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.selected.genre = ?1 AND g.win AND g.Shift = ?2")
    Double findNumOfSuccessFromGenreAndShifts(String genre, Integer shifts);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.selected.category = ?1")
    Double findNumOfGamesFromCategory(Integer category);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.selected.category = ?1 AND g.win")
    Double findNumOfSuccessFromCategory(Integer category);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.selected.category = ?1 AND g.win AND g.Shift = ?2")
    Double findNumOfSuccessFromCategoryAndShifts(Integer category, Integer shifts);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.selected.hasName = ?1")
    Double findNumOfGamesWithName(Boolean hasName);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.selected.hasName = ?1 AND g.win")
    Double findNumOfSuccessWithName(Boolean hasName);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.selected.hasName = ?1 AND g.win AND g.Shift = ?2")
    Double findNumOfSuccessWithNameAndShifts(Boolean hasName, Integer shifts);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.selected.hasInitials = ?1")
    Double findNumOfGamesWithInitials(Boolean hasInitials);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.selected.hasInitials = ?1 AND g.win")
    Double findNumOfSuccessWithInitials(Boolean hasInitials);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.selected.hasInitials = ?1 AND g.win AND g.Shift= ?2")
    Double findNumOfSuccessWithInitialsAndShifts(Boolean hasInitials, Integer shifts);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.selected.hasYear = ?1")
    Double findNumOfGamesWithYear(Boolean hasYear);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.selected.hasYear = ?1 AND g.win")
    Double findNumOfSuccessWithYear(Boolean hasYear);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.selected.hasYear = ?1 AND g.win AND g.Shift = ?2")
    Double findNumOfSuccessWithYearAndShifts(Boolean hasYear, Integer shifts);

    @Query("SELECT g FROM GameAlone g WHERE g.selected = ?1")
    List<GameAlone> findGamesBySelected(Image image);

    @Query("SELECT i FROM Image i WHERE i.name LIKE %?1%")
    Page<Image> findByName(String name, PageRequest of);
}
