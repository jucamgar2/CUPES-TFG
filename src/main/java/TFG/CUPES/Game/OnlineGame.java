package TFG.CUPES.Game;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    private Integer x;

    private Integer y;

    private Boolean player1IsReady;

    private Boolean player2IsReady;

    private Boolean gameStart;
}
