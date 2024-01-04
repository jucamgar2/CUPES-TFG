package TFG.CUPES.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
		.authorizeHttpRequests((authorize) -> authorize
			.requestMatchers("/resources/**","/webjars/**","/h2-console/**","/css/**","/fonts/**","/images/**","/autocomplete/**","/js/**","/stand/**","/start/**","/statistics/**","/","/welcome","/webapp/**","/WEB-INF/**","/lobby").permitAll()
            .requestMatchers("/login/**").permitAll()
            .requestMatchers("/players/new/**").permitAll()
            .requestMatchers("/h2-console/**").permitAll()
            .requestMatchers("/game/onlineGame/**").authenticated()
            .requestMatchers("/game/**").permitAll()
            .requestMatchers("/administration/**").hasAuthority("admin")
            .requestMatchers("/players/profile/**","/players/edit").authenticated()
            .requestMatchers("/statistics/**").permitAll()
			.anyRequest().denyAll()
		)
		.formLogin(login -> login
                .loginPage("/login")
				.defaultSuccessUrl("/")
				.permitAll()
                .failureUrl("/login?error=true"))
		.logout(logout -> logout
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) 
            .invalidateHttpSession(true) 
            .deleteCookies("JSESSIONID") 
            .logoutSuccessUrl("/welcome")
            .permitAll()); 
        return http.build();
    }

    @Bean
	PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
	}
	
    @Bean
    UserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.setUsersByUsernameQuery("select username, password, enabled from player where username = ?");
        userDetailsManager.setAuthoritiesByUsernameQuery("select username, authority from authorities where username = ?");
        return userDetailsManager;
    }
    
}
