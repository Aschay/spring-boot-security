package com.securewebservice.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.securewebservice.DTO.*;
import com.securewebservice.config.jwt.JwtProvider;
import com.securewebservice.model.UserApp;
import com.securewebservice.service.UserAppService;
//import com.securewebservice.service.UserDetailsServiceCustom;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController()
public class AuthController {

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserAppService userService;

	@PostMapping("auth/signin")
	public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {
			String username = authRequest.getUsername();
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
			String token = jwtProvider.generateToken(username);
			return ResponseEntity.ok(new AuthResponse(token, username, authorities));
		} else {
			throw new BadCredentialsException("Username or password invalid");
		}
	}

	@PostMapping("auth/register")
	public ResponseEntity<?> addNewUser(@Valid @RequestBody UserApp userApp) {
		if (userService.existsByUsername(userApp.getUsername())) {
			throw new RuntimeException("User Already Exist");
		}
		userService.addUser(userApp);
		return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
	}

	SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

	@PostMapping("/auth/logout")
	public String performLogout(Authentication authentication, HttpServletRequest request,
			HttpServletResponse response) {
		this.logoutHandler.logout(request, response, authentication);
		return "redirect:/home";
	}

}
