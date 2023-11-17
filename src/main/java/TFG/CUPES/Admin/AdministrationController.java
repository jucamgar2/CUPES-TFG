package TFG.CUPES.Admin;

import java.security.Principal;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import TFG.CUPES.Image.Image;
import TFG.CUPES.Image.ImageForm;
import TFG.CUPES.Image.ImageService;
import TFG.CUPES.Player.Authorities;
import TFG.CUPES.Player.AuthoritiesService;
import TFG.CUPES.Player.Player;
import TFG.CUPES.Player.PlayerService;


import java.io.IOException;


@Controller
@RequestMapping("/administration")
public class AdministrationController {

    private static final String NEW_IMAGE = "administration/newImage";
    private static final String LIST_IMAGE= "administration/images";
    private static final String LIST_PLAYERS= "administration/Players";

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
            }else if(imageForm.getName()==null || imageForm.getName().isEmpty()){
                res = new ModelAndView(NEW_IMAGE);
                res.addObject("errors", List.of("No se ha introducido ningún nombre"));
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

    @GetMapping("/images")
    public ModelAndView images(@RequestParam(name = "page", defaultValue = "0") int page,
                            @RequestParam(name = "size", defaultValue = "10") int size) {
        Page<Image> imagesPage = imageService.getAllLogosPageable(PageRequest.of(page, size));
        ModelAndView res = new ModelAndView(LIST_IMAGE);
        res.addObject("images", imagesPage.getContent());
        res.addObject("currentPage", page);
        res.addObject("totalPages", imagesPage.getTotalPages());
        return res;
    }

    @GetMapping("/images/delete/{id}")
    public ModelAndView deleteImage(@PathVariable("id") int id){
        ModelAndView res = new ModelAndView("redirect:/administration/images");
        Image image = imageService.getLogoById(id);
        if(image!=null){
            imageService.delete(image);
        }
        return res;
    }

    @GetMapping("/players")
    public ModelAndView player(@RequestParam(name = "page", defaultValue = "0") int page,
                            @RequestParam(name = "size", defaultValue = "10") int size) {
        Page<Player> authoritiesPage = playerService.getAllAuthoritiesPageable(PageRequest.of(page, size));
        ModelAndView res = new ModelAndView(LIST_PLAYERS);
        res.addObject("players", authoritiesPage.getContent());
        res.addObject("currentPage", page);
        res.addObject("totalPages", authoritiesPage.getTotalPages());
        return res;
    }

    @GetMapping("/players/disable/{id}")
    public ModelAndView disablePlayer(@PathVariable("id") String id){
        ModelAndView res = new ModelAndView("redirect:/administration/players");
        Player player = playerService.getByUsername(id);
        if(player!=null){
            player.setEnabled(false);
            playerService.save(player);
        }
        return res;
    }

    @GetMapping("/players/enable/{id}")
    public ModelAndView enablePlayer(@PathVariable("id") String id){
        ModelAndView res = new ModelAndView("redirect:/administration/players");
        Player player = playerService.getByUsername(id);
        if(player!=null){
            player.setEnabled(true);
            playerService.save(player);
        }
        return res;
    }
}