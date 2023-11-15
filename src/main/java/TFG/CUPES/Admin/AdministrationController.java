package TFG.CUPES.Admin;

import java.security.Principal;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import TFG.CUPES.Game.Position;
import TFG.CUPES.Game.PositionService;
import TFG.CUPES.Image.Image;
import TFG.CUPES.Image.ImageForm;
import TFG.CUPES.Image.ImageService;
import TFG.CUPES.Player.Authorities;
import TFG.CUPES.Player.AuthoritiesService;
import TFG.CUPES.Player.Player;
import TFG.CUPES.Player.PlayerService;
import net.coobird.thumbnailator.Thumbnails;

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

    @Autowired
    private AdministrationService administrationService;
    
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
            if(imageForm == null ||imageForm.getFile().isEmpty()){
                res = new ModelAndView(NEW_IMAGE);
                res.addObject("errors", List.of("No se ha seleccionado ninguna imagen"));
                return res;
            }else{
                if(imageForm.getFile().getOriginalFilename()==null || imageForm.getFile().getOriginalFilename().isEmpty() ||!(imageForm.getFile().getOriginalFilename().toLowerCase().endsWith(".png")||imageForm.getFile().getOriginalFilename().toLowerCase().endsWith(".jpg")||imageForm.getFile().getOriginalFilename().toLowerCase().endsWith(".jpeg"))){
                    res = new ModelAndView(NEW_IMAGE);
                    res.addObject("errors", List.of("El archivo seleccionado no es una imagen o no es un archivo png o jpg"));
                    return res;
                }else{
                    System.out.println("intento de redimensión");
                    administrationService.resizeInputImage(imageForm);
                }
            }
            Image image = new Image();
            image.setId(imageService.getMaxId()+1);
            image.setName(imageForm.getName());
            image.setResourceName(imageForm.getName().toLowerCase().strip());
            image.setImageType("Logo");
            imageService.save(image);
        }
        res.addObject("imageForm", imageForm);
        return res;
    }
}
