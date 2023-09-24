package TFG.CUPES;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;
import javax.servlet.Filter;

@SpringBootApplication
public class CupesApplication {

	@Bean
    public Filter myCharacterEncodingFilter()  {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }

	public static void main(String[] args) {
		SpringApplication.run(CupesApplication.class, args);
	}

}
