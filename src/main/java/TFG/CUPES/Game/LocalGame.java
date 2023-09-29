package TFG.CUPES.Game;


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
public class LocalGame  extends MultiplayerGame{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    
    private String player1Name;

    private String player2Name;

    @ManyToOne
    private Image player1Image;

    @ManyToOne
    private Image player2Image;   

    private String actualPlayer;

    private Integer x;

    private Integer y;

    private String token;
}
