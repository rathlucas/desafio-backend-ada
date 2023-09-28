package com.lucas.DesafioBackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;

import java.util.logging.Logger;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private static final String[] AUTH_WHITELIST = { "/v3/api-docs/**",
            "/swagger-ui/**", "/swagger-ui.html", "/login" };

	@Bean
	public DefaultSecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
		http
				.csrf()
				.disable()
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers(AUTH_WHITELIST).permitAll()
						.anyRequest().authenticated()
				).httpBasic(withDefaults())
				.formLogin(form -> form
						.defaultSuccessUrl("/swagger-ui.html"));

		return http.build();
	}

	@Bean
	public InMemoryUserDetailsManager userDetailsService() {
		String generatedPassword = passwordEncoder().encode("password");
		Logger.getLogger("spring.security.test").info("Test Generated Password: " + generatedPassword);
		UserDetails user = User
				.withUsername("user")
				.password(generatedPassword)
				.roles("USER")
				.build();
		return new InMemoryUserDetailsManager(user);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
