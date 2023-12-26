package TFG.CUPES.entities;
import java.util.ArrayList;
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

    public List<String> chekcLocalGame(){
        List<String> res = new ArrayList<String>();
        if(this.player1Name==null || this.player1Name==""){
            res.add("El nombre del jugador 1 no puede estar vacío");
        }
        if(this.player2Name==null || this.player2Name==""){
            res.add("El nombre del jugador 2 no puede estar vacío");
        }
        if(this.player1Name == this.player2Name){
            res.add("Los nombres de los jugadores no pueden ser iguales");
        }
        if(this.player1Name!=null && (this.player1Name.length()<4 || this.player1Name.length()>30)){
            res.add("El nombre del jugador 1 debe tener entre 4 y 30 caracteres");
        }
        if(this.player2Name!=null&& (this.player2Name.length()<4 || this.player2Name.length()>30)){
            res.add("El nombre del jugador 2 debe tener entre 4 y 30 caracteres");
        }
        return res;
    }
}
