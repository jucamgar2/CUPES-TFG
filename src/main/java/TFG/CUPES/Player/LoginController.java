package TFG.CUPES.Player;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String login(@PathParam("error") String error, Model model) {
        if (error != null) {
            List<String> errors = List.of("Lo sentimos pero el usuario y la contraseña no coinciden con los de ningun usuario registrado");
            model.addAttribute("errors", errors);
        }
        return "login";
    }
}
