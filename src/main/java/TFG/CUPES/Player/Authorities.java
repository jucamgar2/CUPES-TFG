package TFG.CUPES.Player;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "authorities")
public class Authorities {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

	@ManyToOne
	@JoinColumn(name = "username")
	Player player;
	
	String authority;
	
	
}


