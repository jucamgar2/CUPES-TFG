package TFG.CUPES.Game;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameAloneRepository extends CrudRepository<GameAlone, Integer> {

    @Query("SELECT g FROM GameAlone g WHERE g.token = ?1 AND g.id = ?2")
    Optional<GameAlone> getGameByTokenAndId(String token, Integer id);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.player.username = ?1 AND g.isFinish = true")
    Integer findNumOfGamesFromUser(String username);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.win = true AND g.player.username = ?1")
    Integer findNumOfWinsFromUser(String username);
    
    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.player.username = ?1 AND g.win = true AND g.Shift = 1")
    Integer findNumOfWinsWihtOneShiftFromUser(String username);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.player.username = ?1 AND g.win = true AND g.Shift = 2")
    Integer findNumOfWinsWithTwoShiftsFromUser(String username);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.player.username = ?1 AND g.win = true AND g.Shift = 3")
    Integer findNumOfWinsWithThreeShiftsFromUser(String username);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.player.username = ?1 AND g.win = true AND g.Shift = 4")
    Integer findNumOfWinsWithFourShiftsFromUser(String username);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.isFinish = true")
    Integer findNumOfFinishGames();

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.win = true")
    Integer findNumOfWins();

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.win = true AND g.Shift = 1")
    Integer findNumOfWinsWihtOneShift();

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.win = true AND g.Shift = 2")
    Integer findNumOfWinsWithTwoShifts();

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.win = true AND g.Shift = 3")
    Integer findNumOfWinsWithThreeShifts();

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.win = true AND g.Shift = 4")
    Integer findNumOfWinsWithFourShifts();

    @Query("SELECT g.player.username, COUNT(g) FROM GameAlone g WHERE g.isFinish = true GROUP BY g.player.username ORDER BY COUNT(g) DESC")
    List<Object[]> getRankingGame(Pageable pageable);

    @Query("SELECT g.player.username, COUNT(g) FROM GameAlone g WHERE g.win = true GROUP BY g.player.username ORDER BY COUNT(g) DESC")
    List<Object[]> getRankingWin(Pageable pageable);

}