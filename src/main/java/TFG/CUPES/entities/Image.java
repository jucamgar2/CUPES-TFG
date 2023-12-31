package TFG.CUPES.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    
    private String country;

    private String genre;

    private Integer category;    
    
    private Boolean hasName;

    private Boolean hasInitials;

    private Boolean hasYear;

    private Boolean enabled;
}