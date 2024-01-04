package TFG.CUPES.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import TFG.CUPES.entities.GameAlone;

@Repository
public interface GameAloneRepository extends CrudRepository<GameAlone, Integer> {

    @Query("SELECT g FROM GameAlone g WHERE g.token = ?1 AND g.id = ?2")
    Optional<GameAlone> getGameByTokenAndId(String token, Integer id);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.player.username = ?1 AND g.isFinish = true")
    Double findNumOfGamesFromUser(String username);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.win = true AND g.player.username = ?1")
    Double findNumOfWinsFromUser(String username);
    
    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.player.username = ?1 AND g.win = true AND g.Shift = 1")
    Double findNumOfWinsWihtOneShiftFromUser(String username);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.player.username = ?1 AND g.win = true AND g.Shift = 2")
    Double findNumOfWinsWithTwoShiftsFromUser(String username);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.player.username = ?1 AND g.win = true AND g.Shift = 3")
    Double findNumOfWinsWithThreeShiftsFromUser(String username);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.player.username = ?1 AND g.win = true AND g.Shift = 4")
    Double findNumOfWinsWithFourShiftsFromUser(String username);

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.isFinish = true")
    Double findNumOfFinishGames();

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.win = true")
    Double findNumOfWins();

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.win = true AND g.Shift = 1")
    Double findNumOfWinsWihtOneShift();

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.win = true AND g.Shift = 2")
    Double findNumOfWinsWithTwoShifts();

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.win = true AND g.Shift = 3")
    Double findNumOfWinsWithThreeShifts();

    @Query("SELECT COUNT(g) FROM GameAlone g WHERE g.win = true AND g.Shift = 4")
    Double findNumOfWinsWithFourShifts();

    @Query("SELECT g.player.username, COUNT(g) FROM GameAlone g WHERE g.isFinish = true GROUP BY g.player.username ORDER BY COUNT(g) DESC")
    List<Object[]> getRankingGame(Pageable pageable);

    @Query("SELECT g.player.username, COUNT(g) FROM GameAlone g WHERE g.win = true GROUP BY g.player.username ORDER BY COUNT(g) DESC")
    List<Object[]> getRankingWin(Pageable pageable);

    @Query("SELECT g FROM GameAlone g WHERE g.date BETWEEN ?1 AND ?2")
    List<GameAlone> findAllByGameDateBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT g FROM GameAlone g WHERE g.date >= ?1")
    List<GameAlone> findAllByGameDateAfter(LocalDateTime start);

    @Query("SELECT g FROM GameAlone g WHERE g.date <= ?1")
    List<GameAlone> findAllByGameDateBefore(LocalDateTime end);

}