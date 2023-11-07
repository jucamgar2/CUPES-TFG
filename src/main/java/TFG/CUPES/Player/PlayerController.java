package TFG.CUPES.Player;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    public ModelAndView saveNewPlayer(Player p, BindingResult br){
        if(br.hasErrors()){
            return new ModelAndView("/players/createPlayer",br.getModel());
        }
        List<String> errors = this.playerService.checkPlayerRestrictions(p);
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

    
}
