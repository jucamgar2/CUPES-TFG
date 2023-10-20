package TFG.CUPES.Game;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OnlineGameRepository extends CrudRepository<OnlineGame, Integer>{
    
    @Query("SELECT g FROM OnlineGame g WHERE g.player2 IS NULL")
    List<OnlineGame> findNotStartedGames();

    @Query("SELECT COUNT(g) FROM OnlineGame g WHERE g.winner IS NOT NULL")
    Integer findNumOfGames();

    @Query("SELECT COUNT(g) FROM OnlineGame g WHERE g.winner = g.player1.username")
    Integer findNumOfWinsByPlayer1();

    @Query("SELECT COUNT(g) FROM OnlineGame g WHERE g.winner = g.player2.username")
    Integer findNumOfWinsByPlayer2();

    @Query("SELECT COUNT(g) FROM OnlineGame g WHERE g.player1Shifts = 3 OR g.player2Shifts = 3 AND g.winner IS NOT NULL")
    Integer findNumOfPerfectWins();

    @Query("SELECT AVG(g.player1Shifts) FROM OnlineGame g WHERE g.winner = g.player1.username AND g.winner IS NOT NULL")
    Double findAverageOfShiftsToWinByPlayer1();

    @Query("SELECT AVG(g.player2Shifts) FROM OnlineGame g WHERE g.winner = g.player2.username AND g.winner IS NOT NULL")
    Double findAverageOfShiftsToWinByPlayer2();

    @Query("SELECT COUNT(g) FROM OnlineGame g WHERE g.player1.username = ?1 OR g.player2.username = ?1")
    Integer findNumOfGamesFromUser(String username);

    @Query("SELECT COUNT(g) FROM OnlineGame g WHERE g.winner = g.player1.username AND g.player1.username = ?1 OR g.winner = g.player2.username AND g.player2.username = ?1")
    Integer findNumOfWinsFromUser(String username);

    @Query("SELECT COUNT(g) FROM OnlineGame g WHERE g.player1Shifts = 3 AND g.winner = g.player1.username AND g.player1.username = ?1 OR g.player2Shifts = 3 AND g.winner = g.player2.username AND g.player2.username = ?1")
    Integer findNumOfPerfectWinsFromUser(String username);

    @Query("SELECT AVG(g) FROM OnlineGame g WHERE g.winner = g.player1.username AND g.player1.username = ?1 OR g.winner = g.player2.username AND g.player2.username = ?1")
    Double findAverageNumOfShiftsToWinByUser(String username);

    @Query("SELECT g.winner, COUNT(g) FROM OnlineGame g GROUP BY g.winner ORDER BY COUNT(g) DESC")
    List<Object[]> getRankingGame(Pageable pageable);

    @Query("SELECT p.username, COUNT(g) FROM Player p JOIN OnlineGame g ON (p = g.player1 OR p = g.player2) GROUP BY p.username ORDER BY COUNT(g) DESC")
    List<Object[]> getRankingWin(Pageable pageable);

}