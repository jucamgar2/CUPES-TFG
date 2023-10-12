package TFG.CUPES.Game;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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

    @ManyToMany
    private List<Position> player1Positions;

    @ManyToMany
    private List<Position> player2Positions;

    List<String> chekcLocalGame(){
        List<String> res = new ArrayList<String>();
        if(this.player1Name==null || this.player1Name==""){
            res.add("El nombre del jugador 1 no puede estar vacío");
        }
        if(this.player2Name==null || this.player2Name==""){
            res.add("El nombre del jugador 2 no puede estar vacío");
        }
        return res;
    }
}
