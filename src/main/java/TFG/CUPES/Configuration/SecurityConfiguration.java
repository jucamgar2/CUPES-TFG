package TFG.CUPES.Configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/resources/**","/webjars/**","/h2-console/**","/css/**","/fonts/**","/images/**","/autocomplete/**","/players/new/**","/js/**").permitAll()
				.antMatchers(HttpMethod.GET, "/","welcome","/oups","/home").permitAll()
				.antMatchers("/game/onlineGame/**").authenticated()
				.antMatchers("/game/**").permitAll()
				.anyRequest().denyAll()
				.and()
					.formLogin()
						.loginPage("/login")
						.permitAll()
						.defaultSuccessUrl("/")
				 		.failureUrl("/login?error=true")
				.and()
					.logout()
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) 
                		.invalidateHttpSession(true) 
                		.deleteCookies("JSESSIONID") 
						.logoutSuccessUrl("/welcome")
						.permitAll(); 
                http.csrf().ignoringAntMatchers("/h2-console/**");
                http.headers().frameOptions().sameOrigin();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
	      	.dataSource(dataSource)
	      	.usersByUsernameQuery(
	       	"select username,password,enabled "
	        + "from player "
	        + "where username = ?")
		  	.authoritiesByUsernameQuery(
	       	"select username, authority "
	        + "from authorities "
	        + "where username = ?")
	      	.passwordEncoder(passwordEncoder());	
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {	    
		PasswordEncoder encoder =  new BCryptPasswordEncoder();
	    return encoder;
	}
}


