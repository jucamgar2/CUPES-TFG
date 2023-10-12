package TFG.CUPES.Game;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import TFG.CUPES.Image.Image;
import TFG.CUPES.Player.Player;
import lombok.Getter;
import lombok.Setter;

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
}
