package TFG.CUPES.Game;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import TFG.CUPES.Image.Image;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LocalGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    
    private String player1Name;

    private String player2Name;

    @ManyToOne
    private Image player1Image;

    @ManyToOne
    private Image player2Image;
    
    private Integer player1Shifts;

    private Integer player2Shifts;

    private LocalDateTime player1Start;

    private LocalDateTime player2Start;

    private LocalDateTime player1FInish;

    private LocalDateTime player2Finish;

    private String actualPlayer;

    private String winner;

    private Integer x;

    private Integer y;
}
