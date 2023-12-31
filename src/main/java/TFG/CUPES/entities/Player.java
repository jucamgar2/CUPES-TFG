package TFG.CUPES.entities;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

    @JsonIgnore
    String password;

    Boolean enabled;

    LocalDate birthDate;
    
    String mail;

    String name;

}