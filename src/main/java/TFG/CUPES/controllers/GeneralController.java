package TFG.CUPES.controllers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.util.StreamUtils;

import TFG.CUPES.services.ImageService;


@Controller
public class GeneralController {


	@Autowired
    private DataSource dataSource;

	@Autowired
	ImageService imageService;

    @GetMapping({"/","/welcome"})
	public ModelAndView welcome(Map<String, Object> model,@RequestParam(name="succes",required = false) Boolean succes) {
		if(imageService.getAllLogos().isEmpty()){
			try {
                ClassPathResource resource = new ClassPathResource("data.sql");
                String sqlScript = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
                JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
                jdbcTemplate.execute(sqlScript);

                System.out.println( "Datos cargados exitosamente desde el archivo.");
            } catch (IOException e) {
                System.out.println("Error al leer el archivo data.sql: " + e.getMessage());
            }
		}
		ModelAndView res = new ModelAndView("welcome");
		if(succes!=null && succes){
			res.addObject("succes",true);
		}
	    return res;
	}

	@GetMapping("/manual")
	public String manual(){
		return "manual";
	}

	@GetMapping("/manual/GA")
	public String manualGA(){
		return "manualGA";
	}

	@GetMapping("/manual/LG")
	public String manualLG(){
		return "manualLG";
	}

	@GetMapping("/manual/OG")
	public String manualOG(){
		return "manualOG";
	}
    
}
