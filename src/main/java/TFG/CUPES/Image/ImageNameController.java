package TFG.CUPES.Image;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ImageNameController {

    ImageService logoService;


    @Autowired
    public ImageNameController(ImageService logoService) {
        this.logoService = logoService;
    }

    @GetMapping("/autocomplete/logos")
    @ResponseBody
    public List<String> getAutocompleteWords() {
        List<String> logosName = logoService.getAllLogos().stream().map(x->x.getName()).toList();
        return logosName;
    }

    @GetMapping("/autocomplete")
    public String autocompletePage() {
        return "autocomplete";
    }

   
    
}
