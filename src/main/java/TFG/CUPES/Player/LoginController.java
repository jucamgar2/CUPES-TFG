package TFG.CUPES.Player;

import javax.websocket.server.PathParam;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String login(@PathParam("error") String error, Model model) {
        if (error != null) {
            model.addAttribute("error", true);
        }
        return "login";
    }
}
