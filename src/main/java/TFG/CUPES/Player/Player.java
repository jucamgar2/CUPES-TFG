package TFG.CUPES.Player;


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
    
}
