package TFG.CUPES.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class OnlineGame extends MultiplayerGame{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    private Player player1;

    @ManyToOne
    private Player player2;

    @ManyToOne
    private Image player1Image1;

    @ManyToOne
    private Image player2Image1;

    @ManyToOne
    private Image player1Image2;

    @ManyToOne
    private Image player2Image2;

    @ManyToOne
    private Image player1Image3;

    @ManyToOne
    private Image player2Image3;

    private Integer player1X;

    private Integer player2X;

    private Integer player1Y;

    private Integer player2Y;

    private Boolean player1IsReady;

    private Boolean player2IsReady;

    private Boolean gameStart;

    private Boolean player1Leaves;

    private Boolean player2Leaves;

    private Integer player1Succes;

    private Integer player2Succes;

    private Integer currentPlayer1Image;

    private Integer currentPlayer2Image;

    @ManyToMany
    private List<Position> player1Positions;

    @ManyToMany
    private List<Position> player2Positions;

    private Boolean player1Redt;

    private Boolean player2Redt;

    private LocalDateTime creationDate;

    public void removePlayer2(){
        this.player2 = null;
    }
}

