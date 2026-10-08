package backend.bookstore;

//import java.util.ArrayList;
//import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;


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
			.loginPage("/login")                          // Custom login page.
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
  
/*   
    @Bean
    public UserDetailsService userDetailsService(BCryptPasswordEncoder passwordEncoder) {
        List<UserDetails> users = new ArrayList<>();

        UserDetails user1 = User.withUsername("user")
            .password(passwordEncoder.encode("user"))
            .roles("USER")
            .build();
       
        users.add(user1);

        UserDetails user2 = User.withUsername("admin")
            .password(passwordEncoder.encode("admin"))
            .roles("ADMIN")
            .build();
       
        users.add(user2);

        return new InMemoryUserDetailsManager(users);
    } 
*/
 
}