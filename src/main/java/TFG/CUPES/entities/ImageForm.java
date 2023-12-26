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


}
