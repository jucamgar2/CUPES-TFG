package TFG.CUPES;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GeneralController {

    @GetMapping({"/","/welcome"})
	public String welcome(Map<String, Object> model) {	    
	    return "welcome";
	}

	@GetMapping("/manual")
	public String manual(){
		return "TODO";
	}
    
}
