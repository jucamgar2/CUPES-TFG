package TFG.CUPES.entities;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageForm {
    
    private MultipartFile file;

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
