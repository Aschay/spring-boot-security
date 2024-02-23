package com.securewebservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive;

import com.securewebservice.config.jwt.JwtAuthFilter;
import com.securewebservice.config.jwt.JwtAuthenEntryPoint;
import com.securewebservice.config.jwt.JwtAuthoHandler;
import com.securewebservice.service.UserDetailsServiceCustom;

import org.springframework.beans.factory.annotation.Autowired;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class WebSecurityConfig {

	@Autowired
	JwtAuthFilter authFilter;

	@Autowired
	private JwtAuthenEntryPoint jwtAuthen;
	
	@Autowired
	private JwtAuthoHandler jwtAutho;


	@Autowired
	UserDetailsServiceCustom userDetailsService;

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("auth/signin", "auth/register","/auth/logout", "/home");
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable);
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		//custom authorization and authentication
		http.exceptionHandling(exception -> exception .accessDeniedHandler(jwtAutho)
				                                      .authenticationEntryPoint(jwtAuthen));
		http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
		HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(Directive.COOKIES));
		http.logout(logout -> logout 
				                 .logoutUrl("/auth/logout")
		                         .logoutSuccessUrl("/home")
		                         .permitAll()
		                         .addLogoutHandler(clearSiteData)
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