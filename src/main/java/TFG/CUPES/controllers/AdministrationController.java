package TFG.CUPES.controllers;

import java.io.IOException;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import TFG.CUPES.DTOdata.ImageAndGamesDTO;
import TFG.CUPES.components.GameUtils;
import TFG.CUPES.entities.Authorities;
import TFG.CUPES.entities.GameAlone;
import TFG.CUPES.entities.Image;
import TFG.CUPES.entities.ImageForm;
import TFG.CUPES.entities.Player;
import TFG.CUPES.entities.Position;
import TFG.CUPES.services.AdministrationService;
import TFG.CUPES.services.AuthoritiesService;
import TFG.CUPES.services.ImageService;
import TFG.CUPES.services.PlayerService;
import TFG.CUPES.services.PositionService;
import jakarta.servlet.http.HttpServletResponse;


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

    GameUtils gameUtils = new GameUtils();

    @Autowired
    private PositionService positionService;
    
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
            image.setCountry(imageForm.getCountry());
            image.setGenre(imageForm.getGenre());
            image.setCategory(imageForm.getCategory());
            image.setHasName(imageForm.getHasName());
            image.setHasInitials(imageForm.getHasInitials());
            image.setHasYear(imageForm.getHasYear());
            image.setEnabled(imageForm.getEnabled());
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

    @GetMapping("/images/view/{id}")
    public ModelAndView viewImage(@PathVariable("id") int id) throws IOException{
        ModelAndView res = new ModelAndView("administration/viewImage");
        Image image = imageService.getLogoById(id);
        if(image == null){
            return new ModelAndView("redirect:/administration/images");
        }
        List<Position> positions = positionService.findAll();
        Map<Position, String> positionsMap = new HashMap<>();
        String imageSelected = "/images/Logo/"+image.getResourceName()+".jpg";
        for (Position position : positions) {
            if(! gameUtils.checkImageHasMoreThan1Color(imageSelected, position)){
                positionsMap.put(position,"Trozo no valido");
            }
        }
        DecimalFormat df = new DecimalFormat("#.##");
        DecimalFormat df1 = new DecimalFormat("#");
        Double games = this.imageService.getGamesFromImage(image);
        Double success = this.imageService.getSuccessFromImage(image);
        Double successRate = success>0?success/games:0.0;
        res.addObject("games", df1.format(games));
        res.addObject("success", df1.format(success));
        res.addObject("successRate",df.format(successRate));
        res.addObject("img", image);
        res.addObject("image", imageSelected);
        res.addObject("positions", positionsMap);
        return res;
    }

    @GetMapping("/images/disable/{id}")
    public ModelAndView deleteImage(@PathVariable("id") int id){
        ModelAndView res = new ModelAndView("redirect:/administration/images");
        Image image = imageService.getLogoById(id);
        if(image!=null){
            image.setEnabled(false);
            imageService.save(image);
        }
        return res;
    }

    @GetMapping("/images/enable/{id}")
    public ModelAndView enableImage(@PathVariable("id") int id){
        ModelAndView res = new ModelAndView("redirect:/administration/images");
        Image image = imageService.getLogoById(id);
        if(image!=null){
            image.setEnabled(true);
            imageService.save(image);
        }
        return res;
    }

    @GetMapping("/images/exportData/{id}")
    public void exportData(@PathVariable("id") int id, HttpServletResponse response) throws IOException {
        Image image = imageService.getLogoById(id);
        if (image != null) {
            List<GameAlone> games = imageService.getGamesFromSelected(image);
            ImageAndGamesDTO dataToExport = new ImageAndGamesDTO(image, games);
            response.setContentType("application/json");
            response.setHeader("Content-Disposition", "attachment; filename=games_by_image.json");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.writeValue(response.getOutputStream(), dataToExport);
        }
    }

    @GetMapping("/images/statistics")
    public ModelAndView imageStatistics(){
        DecimalFormat df = new DecimalFormat("#.##");
        ModelAndView res = new ModelAndView("administration/adminStatistics");
        res.addObject("spainSuccess", df.format(imageService.getSuccesRateFromCountry("España")*100));
        res.addObject("spainSuccess1", df.format(imageService.getSuccesRateFromCountryAndShifts("España",1)*100));
        res.addObject("spainSuccess2", df.format(imageService.getSuccesRateFromCountryAndShifts("España",2)*100));
        res.addObject("spainSuccess3", df.format(imageService.getSuccesRateFromCountryAndShifts("España",3)*100));
        res.addObject("spainSuccess4", df.format(imageService.getSuccesRateFromCountryAndShifts("España",4)*100));
        res.addObject("germanySuccess", df.format(imageService.getSuccesRateFromCountry("Alemania")*100));
        res.addObject("germanySuccess1", df.format(imageService.getSuccesRateFromCountryAndShifts("Alemania",1)*100));
        res.addObject("germanySuccess2", df.format(imageService.getSuccesRateFromCountryAndShifts("Alemania",2)*100));
        res.addObject("germanySuccess3", df.format(imageService.getSuccesRateFromCountryAndShifts("Alemania",3)*100));
        res.addObject("germanySuccess4", df.format(imageService.getSuccesRateFromCountryAndShifts("Alemania",4)*100));
        res.addObject("franceSuccess", df.format(imageService.getSuccesRateFromCountry("Francia")*100));
        res.addObject("franceSuccess1", df.format(imageService.getSuccesRateFromCountryAndShifts("Francia",1)*100));
        res.addObject("franceSuccess2", df.format(imageService.getSuccesRateFromCountryAndShifts("Francia",2)*100));
        res.addObject("franceSuccess3", df.format(imageService.getSuccesRateFromCountryAndShifts("Francia",3)*100));
        res.addObject("franceSuccess4", df.format(imageService.getSuccesRateFromCountryAndShifts("Francia",4)*100));
        res.addObject("italySuccess", df.format(imageService.getSuccesRateFromCountry("Italia")*100));
        res.addObject("italySuccess1", df.format(imageService.getSuccesRateFromCountryAndShifts("Italia",1)*100));
        res.addObject("italySuccess2", df.format(imageService.getSuccesRateFromCountryAndShifts("Italia",2)*100));
        res.addObject("italySuccess3", df.format(imageService.getSuccesRateFromCountryAndShifts("Italia",3)*100));
        res.addObject("italySuccess4", df.format(imageService.getSuccesRateFromCountryAndShifts("Italia",4)*100));
        res.addObject("ukSuccess", df.format(imageService.getSuccesRateFromCountry("Inglaterra")*100));
        res.addObject("ukSuccess1", df.format(imageService.getSuccesRateFromCountryAndShifts("Inglaterra",1)*100));
        res.addObject("ukSuccess2", df.format(imageService.getSuccesRateFromCountryAndShifts("Inglaterra",2)*100));
        res.addObject("ukSuccess3", df.format(imageService.getSuccesRateFromCountryAndShifts("Inglaterra",3)*100));
        res.addObject("ukSuccess4", df.format(imageService.getSuccesRateFromCountryAndShifts("Inglaterra",4)*100));
        res.addObject("usaSuccess", df.format(imageService.getSuccesRateFromCountry("Estados Unidos")*100));
        res.addObject("usaSuccess1", df.format(imageService.getSuccesRateFromCountryAndShifts("Estados Unidos",1)*100));
        res.addObject("usaSuccess2", df.format(imageService.getSuccesRateFromCountryAndShifts("Estados Unidos",2)*100));
        res.addObject("usaSuccess3", df.format(imageService.getSuccesRateFromCountryAndShifts("Estados Unidos",3)*100));
        res.addObject("usaSuccess4", df.format(imageService.getSuccesRateFromCountryAndShifts("Estados Unidos",4)*100));
        res.addObject("maleSuccess", df.format(imageService.getSuccesRateFromGenre("M")*100));
        res.addObject("maleSuccess1", df.format(imageService.getSuccesRateFromGenreAndShifts("M",1)*100));
        res.addObject("maleSuccess2", df.format(imageService.getSuccesRateFromGenreAndShifts("M",2)*100));
        res.addObject("maleSuccess3",df.format(imageService.getSuccesRateFromGenreAndShifts("M",3)*100));
        res.addObject("maleSuccess4", df.format(imageService.getSuccesRateFromGenreAndShifts("M",4)*100));
        res.addObject("femaleSuccess", df.format(imageService.getSuccesRateFromGenre("F")*100));
        res.addObject("femaleSuccess1", df.format(imageService.getSuccesRateFromGenreAndShifts("F",1)*100));
        res.addObject("femaleSuccess2", df.format(imageService.getSuccesRateFromGenreAndShifts("F",2)*100));
        res.addObject("femaleSuccess3", df.format(imageService.getSuccesRateFromGenreAndShifts("F",3)*100));
        res.addObject("femaleSuccess4", df.format(imageService.getSuccesRateFromGenreAndShifts("F",4)*100));
        res.addObject("firstDivisionSuccess", df.format(imageService.getSuccesRateFromCategory(1)*100));
        res.addObject("firstDivisionSuccess1", df.format(imageService.getSuccesRateFromCategoryAndShifts(1,1)*100));
        res.addObject("firstDivisionSuccess2", df.format(imageService.getSuccesRateFromCategoryAndShifts(1,2)*100));
        res.addObject("firstDivisionSuccess3", df.format(imageService.getSuccesRateFromCategoryAndShifts(1,3)*100));
        res.addObject("firstDivisionSuccess4", df.format(imageService.getSuccesRateFromCategoryAndShifts(1,4)*100));
        res.addObject("secondDivisionSuccess", df.format(imageService.getSuccesRateFromCategory(2)*100));
        res.addObject("secondDivisionSuccess1", df.format(imageService.getSuccesRateFromCategoryAndShifts(2,1)*100));
        res.addObject("secondDivisionSuccess2", df.format(imageService.getSuccesRateFromCategoryAndShifts(2,2)*100));
        res.addObject("secondDivisionSuccess3", df.format(imageService.getSuccesRateFromCategoryAndShifts(2,3)*100));
        res.addObject("secondDivisionSuccess4", df.format(imageService.getSuccesRateFromCategoryAndShifts(2,4)*100));
        res.addObject("nameSuccess",df.format(imageService.getSuccesRateFromName(true)*100));
        res.addObject("nameSuccess1",df.format(imageService.getSuccesRateFromNameAndShifts(true,1)*100));
        res.addObject("nameSuccess2",df.format(imageService.getSuccesRateFromNameAndShifts(true,2)*100));
        res.addObject("nameSuccess3",df.format(imageService.getSuccesRateFromNameAndShifts(true,3)*100));
        res.addObject("nameSuccess4",df.format(imageService.getSuccesRateFromNameAndShifts(true,4)*100));
        res.addObject("noNameSuccess",df.format(imageService.getSuccesRateFromName(false)*100));
        res.addObject("noNameSuccess1",df.format(imageService.getSuccesRateFromNameAndShifts(false,1)*100));
        res.addObject("noNameSuccess2",df.format(imageService.getSuccesRateFromNameAndShifts(false,2)*100));
        res.addObject("noNameSuccess3",df.format(imageService.getSuccesRateFromNameAndShifts(false,3)*100));
        res.addObject("noNameSuccess4",df.format(imageService.getSuccesRateFromNameAndShifts(false,4)*100));
        res.addObject("initialsSuccess",df.format(imageService.getSuccesRateFromName(true)*100));
        res.addObject("initialsSuccess1",df.format(imageService.getSuccesRateFromNameAndShifts(true,1)*100));
        res.addObject("initialsSuccess2",df.format(imageService.getSuccesRateFromNameAndShifts(true,2)*100));
        res.addObject("initialsSuccess3",df.format(imageService.getSuccesRateFromNameAndShifts(true,3)*100));
        res.addObject("initialsSuccess4",df.format(imageService.getSuccesRateFromNameAndShifts(true,4)*100));
        res.addObject("noInitialsSuccess",df.format(imageService.getSuccesRateFromName(false)*100));
        res.addObject("noInitialsSuccess1",df.format(imageService.getSuccesRateFromNameAndShifts(false,1)*100));
        res.addObject("noInitialsSuccess2",df.format(imageService.getSuccesRateFromNameAndShifts(false,2)*100));
        res.addObject("noInitialsSuccess3",df.format(imageService.getSuccesRateFromNameAndShifts(false,3)*100));
        res.addObject("noInitialsSuccess4",df.format(imageService.getSuccesRateFromNameAndShifts(false,4)*100));
        res.addObject("yearSuccess",df.format(imageService.getSuccesRateFromName(true)*100));
        res.addObject("yearSuccess1",df.format(imageService.getSuccesRateFromNameAndShifts(true,1)*100));
        res.addObject("yearSuccess2",df.format(imageService.getSuccesRateFromNameAndShifts(true,2)*100));
        res.addObject("yearSuccess3",df.format(imageService.getSuccesRateFromNameAndShifts(true,3)*100));
        res.addObject("yearSuccess4",df.format(imageService.getSuccesRateFromNameAndShifts(true,4)*100));
        res.addObject("noYearSuccess",df.format(imageService.getSuccesRateFromName(false)*100));
        res.addObject("noYearSuccess1",df.format(imageService.getSuccesRateFromNameAndShifts(false,1)*100));
        res.addObject("noYearSuccess2",df.format(imageService.getSuccesRateFromNameAndShifts(false,2)*100));
        res.addObject("noYearSuccess3",df.format(imageService.getSuccesRateFromNameAndShifts(false,3)*100));
        res.addObject("noYearSuccess4",df.format(imageService.getSuccesRateFromNameAndShifts(false,4)*100));
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