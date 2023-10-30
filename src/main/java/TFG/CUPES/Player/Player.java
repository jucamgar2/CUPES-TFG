package TFG.CUPES.Player;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Entity
public class Player {
    @Id
    String username;

    String password;

    Boolean enabled;

    LocalDate birthDate;
    
    String mail;

    String name;

}
