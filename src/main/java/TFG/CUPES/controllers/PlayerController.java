package TFG.CUPES.controllers;


import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import TFG.CUPES.entities.Authorities;
import TFG.CUPES.entities.Player;
import TFG.CUPES.services.AuthoritiesService;
import TFG.CUPES.services.PlayerService;

@Controller
@RequestMapping("/players")
public class PlayerController {

    PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
    
    PlayerService playerService;

    AuthoritiesService authoritiesService;

    StatisticsController statisticsController;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(LocalDate.class, new CustomDateEditor(dateFormat, true));
    }

    @Autowired
    public PlayerController(PlayerService playerService, AuthoritiesService authoritiesService,StatisticsController statisticsController){
        this.playerService = playerService;
        this.authoritiesService = authoritiesService;
        this.statisticsController = statisticsController;
    }

    
    @GetMapping("/new")
    public ModelAndView createPlayer(){
        Player p = new Player();
        ModelAndView result = new ModelAndView("players/createPlayer");
        result.addObject("player", p);
        return result;
    }

    @PostMapping("/new")
    public ModelAndView saveNewPlayer(Player p){
        List<String> errors = this.playerService.checkPlayerRestrictions(p);
        if(!errors.isEmpty()){
            ModelAndView result = new ModelAndView("players/createPlayer");
            result.addObject("errors", errors);
            System.out.println(errors);
            return result;
        }
        p.setEnabled(true);
        p.setPassword(passwordEncoder.encode(p.getPassword()));
        System.out.println(p.getPassword());
        //p.setPassword(p.getPassword());
        playerService.save(p);
        Authorities a = new Authorities();
        a.setAuthority("player");
        a.setPlayer(p);
        a.setId(this.authoritiesService.findMaxId()+1);
        authoritiesService.save(a);
        ModelAndView result =new ModelAndView("redirect:/");
        return result;
    }

    @GetMapping("/profile")
    public ModelAndView redirectProfile(String username,Principal principal){
        ModelAndView result = new ModelAndView("redirect:/players/profile/"+principal.getName());
        if(principal==null || principal.getName()==null || principal.getName().equals("")){
            result = new ModelAndView("redirect:/login");
        }
        return result;
    }

    @GetMapping("/profile/{username}")
    public ModelAndView showProfile(@PathVariable("username") String username,Principal principal){ 
        ModelAndView result = new ModelAndView("players/profile");
        String authority = authoritiesService.findByUsername(principal.getName()).getAuthority();
        if(principal==null || principal.getName()==null || principal.getName().equals("")){
            result = new ModelAndView("redirect:/login");
            return result;
        }else if(!authority.equals("admin") && !principal.getName().equals(username)){
            result = new ModelAndView("redirect:/");
            return result;
        }
        Player p = playerService.getByUsername(username);
        result.addObject("player", p);
        result.addObject("principal", principal);
        result =statisticsController.addGameAloneStatisticsByUser(result, username);
        result = statisticsController.addOnlineGameStatisticsByUser(result, username);
        return result;
    }    

    @GetMapping("/edit")
    public ModelAndView editProfile(Principal principal){
        if(principal==null){
            ModelAndView result = new ModelAndView("redirect:/login");
            return result;
        }
        ModelAndView result = new ModelAndView("players/edit");
        Player p = playerService.getByUsername(principal.getName());
        result.addObject("player", p);
        return result;
    }

    @PostMapping("/edit")
    public ModelAndView saveEditProfile(Player p, Principal principal){
        Player player = playerService.getByUsername(principal.getName());
        player.setName(p.getName());
        player.setMail(p.getMail());
        List<String> errors = this.playerService.editPlayerErrors(player);
        if(!errors.isEmpty()){
            ModelAndView result = new ModelAndView("players/edit");
            result.addObject("errors", errors);
            return result;
        }
        playerService.save(player);
        ModelAndView result =new ModelAndView("redirect:/players/profile/"+principal.getName());
        return result;
    }
}
