package com.securewebservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/");
	}

	@Bean
	SecurityFilterChain web(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/user").hasRole("USER")
				.requestMatchers("/admin").hasRole("ADMIN").anyRequest().authenticated())
				// .formLogin(withDefaults()); //login form
				.httpBasic(withDefaults()); // http basic in in header of the http request
		return http.build();
	}

	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(10);
	}

	@Bean
     InMemoryUserDetailsManager userDetailsService() {
		String passwordUser = "user";
		String hashedPasswordUser = encoder().encode(passwordUser);
		System.out.println(hashedPasswordUser);
		UserDetails user = User.builder().username("user").password(hashedPasswordUser).roles("USER").build();
		String passwordAdmin = "admin";
		String hashedPasswordAdmin = encoder().encode(passwordAdmin);
		System.out.println(hashedPasswordAdmin);
		UserDetails admin = User.builder().username("admin").password(hashedPasswordAdmin).roles("USER", "ADMIN")
				.build();
		return new InMemoryUserDetailsManager(user, admin);
	}
}
