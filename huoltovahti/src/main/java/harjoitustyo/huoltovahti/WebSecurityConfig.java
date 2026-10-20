package harjoitustyo.huoltovahti;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity 
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests( authorize -> authorize        
				.requestMatchers("/css/***").permitAll()    // Path/s that doesn't require any authentication.
				.anyRequest().authenticated()                 // All other paths must be authenticated.
			)
            .csrf(csrf -> csrf.disable()) // NOT FOR PRODUCTION! Disable CSRF protection for testing purposes
            .httpBasic(Customizer.withDefaults()) // Enable HTTP Basic authentication
            
		.formLogin( formlogin -> formlogin
			//.loginPage("/login")                          // Custom login page.
			.defaultSuccessUrl("/index", true)      // <-- Tells where to go after
			.permitAll()                                  // Successful login.
		)
		.logout( logout -> logout
			.permitAll()
		);
		return http.build();
	}

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
