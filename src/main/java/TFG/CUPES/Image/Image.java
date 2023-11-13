package TFG.CUPES.Image;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class Image {
    @Id
    private Integer id;
    
    private String name;

    private String resourceName;

    private String imageType;
    
}
