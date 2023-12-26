package TFG.CUPES.controllers;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GeneralController {



    @GetMapping({"/","/welcome"})
	public ModelAndView welcome(Map<String, Object> model) {
		ModelAndView res = new ModelAndView("welcome");
	    return res;
	}

	@GetMapping("/manual")
	public String manual(){
		return "TODO";
	}
    
}
