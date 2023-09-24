package TFG.CUPES.Player;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Entity
public class Player {
    @Id
    @Column(unique=true)
    @Size(min = 3, max = 30, message = "El nombre de usuario tiene que tener una longitud de entre 3 y 30 caracteres")
    String username;

    @Size(min = 4, max = 30, message = "La contraseña tiene que tener una longitud de entre 3 y 30 caracteres")
    String password;

    Boolean enabled;
    
}
