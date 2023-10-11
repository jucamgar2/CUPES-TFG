package TFG.CUPES.Game;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import TFG.CUPES.Image.Image;
import TFG.CUPES.Player.Player;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class GameAlone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Image selected;

    private Integer Shift;

    private Boolean isFinish;

    private Boolean win;

    private Integer x;

    private Integer y;

    private String token;

    @ManyToOne
    private Player player;

    @ManyToMany
    List<Position> positions;
}