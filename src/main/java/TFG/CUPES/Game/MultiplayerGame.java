package TFG.CUPES.Game;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class MultiplayerGame {
    private Integer player1Shifts;

    private Integer player2Shifts;

    private LocalDateTime player1Start;

    private LocalDateTime player2Start;

    private LocalDateTime player1FInish;

    private LocalDateTime player2Finish;

    private String winner;
}
