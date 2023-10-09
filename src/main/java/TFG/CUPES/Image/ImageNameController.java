package TFG.CUPES.Image;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import TFG.CUPES.Game.OnlineGameService;

@Controller
public class ImageNameController {

    ImageService logoService;
    OnlineGameService onlineGameService;

    @Autowired
    public ImageNameController(ImageService logoService, OnlineGameService onlineGameService) {
        this.logoService = logoService;
        this.onlineGameService = onlineGameService;
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
