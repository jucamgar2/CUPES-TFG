package TFG.CUPES.Game;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import TFG.CUPES.Image.Image;

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

}
