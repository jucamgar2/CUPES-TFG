package TFG.CUPES.Admin;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import TFG.CUPES.Image.Image;
import TFG.CUPES.Image.ImageForm;
import TFG.CUPES.Image.ImageService;
import TFG.CUPES.Player.Authorities;
import TFG.CUPES.Player.AuthoritiesService;
import TFG.CUPES.Player.Player;
import TFG.CUPES.Player.PlayerService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/administration")
public class AdministrationController {

    private static final String NEW_IMAGE = "administration/newImage";
    private final Path imageStorageLocation = Paths.get("src/main/resources/static/images/Logo");

    @Autowired
    private ImageService imageService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private AuthoritiesService authoritiesService;
    
    @GetMapping()
    public String administration() {
        return "administration/administration";
    }

    @GetMapping("/images/new")
    public ModelAndView newImage() {
        ModelAndView res = new ModelAndView(NEW_IMAGE);
        ImageForm img = new ImageForm();
        res.addObject("imageForm", img);
        return res;
    }

    @PostMapping(value = "/images/new", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ModelAndView newImage(ImageForm imageForm, Principal principal) throws IOException {
        ModelAndView res = new ModelAndView("redirect:/administration");
        Authorities a = authoritiesService.findByUsername(principal.getName());
        if (a.getAuthority().equals("admin")) {
            Image image = new Image();
            image.setId(999);
            image.setName(imageForm.getName());
            image.setResourceName(imageForm.getName().strip());
            image.setImageType(imageForm.getImageType());
            imageService.save(image);
            Files.copy(imageForm.getFile().getInputStream(), imageStorageLocation.resolve(image.getResourceName()+".jpg"), StandardCopyOption.REPLACE_EXISTING);
        }
        res.addObject("imageForm", imageForm);
        return res;
    }
}
