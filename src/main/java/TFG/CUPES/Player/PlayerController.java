package TFG.CUPES.Player;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/players")
public class PlayerController {

    PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
    
    PlayerService playerService;

    AuthoritiesService authoritiesService;

    @Autowired
    public PlayerController(PlayerService playerService, AuthoritiesService authoritiesService){
        this.playerService = playerService;
        this.authoritiesService = authoritiesService;
    }

    @GetMapping("/new")
    public ModelAndView createPlayer(){
        Player p = new Player();
        ModelAndView result = new ModelAndView("/players/createPlayer");
        result.addObject("player", p);
        return result;
    }

    @PostMapping("/new")
    public ModelAndView saveNewPlayer(@Valid Player p, BindingResult br){
        if(br.hasErrors()){
            return new ModelAndView("/players/createPlayer",br.getModel());
        }
        List<String> errors = checkPlayerRestrictions(p);
        if(!errors.isEmpty()){
            ModelAndView result = new ModelAndView("/players/createPlayer");
            result.addObject("errors", errors);
            return result;
        }
        p.setEnabled(true);
        p.setPassword(passwordEncoder.encode(p.getPassword()));
        playerService.save(p);
        Authorities a = new Authorities();
        a.setAuthority("player");
        a.setPlayer(p);
        a.setId(this.authoritiesService.findMaxId()+1);
        System.out.println("ID: "+a.getId());
        authoritiesService.save(a);
        ModelAndView result =new ModelAndView("redirect:/");
        return result;
    }

    public List<String> checkPlayerRestrictions(Player p){
        List<String> errors = new ArrayList<String>();
        if(p.getUsername().contains(" ")){
            errors.add("El nombre de usuario no debe contener espacios en blanco");
        }
        if(p.getPassword().contains(" ")){
            errors.add("La contraseña no debe contener espacios en blanco");
        }
        if(playerService.exists(p.getUsername())){
            errors.add("Ya existe un usuario con ese nombre");
        }
        if(p.getUsername().length()<5 || p.getUsername().length()>30){
            errors.add("La longitud del nombre de usuario debe tener entre 5 y 30 caracteres");
        }
        if(p.getPassword().length()<4 || p.getPassword().length()>30){
            errors.add("La longitud de la contraseña debe tener entre 4 y 30 caracteres");
        }
        return errors;
    }
}
