package TFG.CUPES.Player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/players")
public class PlayerController {
    
    @Autowired
    PlayerService playerService;

    @GetMapping("/new")
    public ModelAndView createPlayer(){
        Player p = new Player();
        ModelAndView result = new ModelAndView("/players/createPlayer");
        result.addObject("p", p);
        return result;
    }

    @PostMapping("/new")
    public ModelAndView saveNewPlayer(@Validated Player p, BindingResult br){
        if(br.hasErrors()){
            return new ModelAndView("/players/createPlayer",br.getModel());
        }
        playerService.save(p);
        ModelAndView result =new ModelAndView("redirect:/welcome");
        return result;
    }
}
