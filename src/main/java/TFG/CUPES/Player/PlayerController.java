package TFG.CUPES.Player;

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
        ModelAndView errors = checkPlayerRestrictions(p,br);
        if(errors!=null){
            return errors;
        }
        p.setEnabled(true);
        p.setPassword(passwordEncoder.encode(p.getPassword()));
        playerService.save(p);
        Authorities a = new Authorities();
        a.setAuthority("player");
        a.setPlayer(p);
        authoritiesService.save(a);
        ModelAndView result =new ModelAndView("redirect:/");
        return result;
    }

    public ModelAndView checkPlayerRestrictions(Player p, BindingResult br){
        if(p.getUsername().contains(" ") && p.getPassword().contains(" ") ){
            ModelAndView res = new ModelAndView("/players/createPlayer",br.getModel());
            res.addObject("usernameError","El nombre de usuario no debe contener espacios en blanco");
            res.addObject("passwordError","La contraseña no debe contener espacios en banco");
            return res;
        }else if(p.getUsername().contains(" ") ){
            ModelAndView res = new ModelAndView("/players/createPlayer",br.getModel());
            res.addObject("usernameError","El nombre de usuario no debe contener espacios en blanco");
            return res;
        }else if(p.getPassword().contains(" ")){
            ModelAndView res = new ModelAndView("/players/createPlayer",br.getModel());
            res.addObject("passwordError","La contraseña no debe contener espacios en banco");
            return res;
        }
        else{
            if(playerService.exists(p.getUsername())){
            ModelAndView res = new ModelAndView("/players/createPlayer",br.getModel());
            res.addObject("existsError","Ya existe un usuario con ese nombre");
            return res;
            }
            if((p.getUsername().length()<5 || p.getUsername().length()>30) && (p.getPassword().length()<4 || p.getPassword().length()>30)){
                ModelAndView res = new ModelAndView("/players/createPlayer",br.getModel());
                res.addObject("usernameError","La longitud del nombre de usuario debe tener entre 5 y 30 caracteres");
                res.addObject("passwordError","La longitud de la contraseña debe tener entre 5 y 30 caracteres");
                return res;
            }else if(p.getUsername().length()<5 || p.getUsername().length()>30){
                ModelAndView res = new ModelAndView("/players/createPlayer",br.getModel());
                res.addObject("usernameError","La longitud del nombre de usuario debe tener entre 5 y 30 caracteres");
                return res;
            }else if(p.getPassword().length()<4 || p.getPassword().length()>30){
                ModelAndView res = new ModelAndView("/players/createPlayer",br.getModel());
                res.addObject("passwordError","La longitud de la contraseña debe tener entre 4 y 30 caracteres");
                return res;
            } else{
                return null;
            }
        }
    }
}
