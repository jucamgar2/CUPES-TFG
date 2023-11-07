package TFG.CUPES.Player;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
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
