package TFG.CUPES.Game;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class MultiplayerGame {
    private Integer player1Shifts;

    private Integer player2Shifts;

    private LocalDateTime player1Start;

    private LocalDateTime player2Start;

    private LocalDateTime player1FInish;

    private LocalDateTime player2Finish;

    private String winner;


    public String checkWinner(String player1Name, String player2Name) {
        String winner;
        if (player1Shifts < player2Shifts) {
            winner = player1Name;
        } else if (player2Shifts < player1Shifts) {
            winner = player2Name;
        } else {
            Duration player1Time = Duration.between(player1Start, player1FInish);
            Duration player2Time = Duration.between(player2Start, player2Finish);
            if (player1Time.compareTo(player2Time) < 0) {
                winner = player1Name;
            } else if (player2Time.compareTo(player1Time) < 0) {
                winner = player2Name;
            } else {
                winner = "draw";
            }
        }
        return winner;
    }

    
    
}
