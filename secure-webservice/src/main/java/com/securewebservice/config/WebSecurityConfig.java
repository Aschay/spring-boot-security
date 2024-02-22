package com.securewebservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.securewebservice.config.jwt.JwtAuthFilter;
import com.securewebservice.service.UserDetailsServiceCustom;


import org.springframework.beans.factory.annotation.Autowired;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class WebSecurityConfig {

	@Autowired
	JwtAuthFilter authFilter;

	@Autowired
	UserDetailsServiceCustom userDetailsService;

  
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
			return (web) -> web.ignoring().requestMatchers("/gen","/");
	}

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { 
		http.authorizeHttpRequests(auth -> auth
				//  .requestMatchers("/user").hasRole("USER")
				//  .requestMatchers("/admin").hasRole("ADMIN") look at controller added method security expressions
				  .anyRequest().authenticated()
				  );
		http.authenticationProvider(authenticationProvider());
		http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
    	
    }
    	
    @Bean
    public PasswordEncoder passwordEncoder() { 
        return new BCryptPasswordEncoder(); 
    } 
  
    @Bean
    public AuthenticationProvider authenticationProvider() { 
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(); 
        authenticationProvider.setUserDetailsService(userDetailsService); 
        authenticationProvider.setPasswordEncoder(passwordEncoder()); 
        return authenticationProvider; 
    } 
  
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception { 
        return config.getAuthenticationManager(); 
    } 
  
  
}