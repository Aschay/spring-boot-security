package com.securewebservice.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.securewebservice.DTO.AuthorizationRequest;
import com.securewebservice.config.jwt.JwtProvider;

import jakarta.validation.Valid;

@RestController
public class HelloController {

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping(value = "/")
	HashMap<String, String> welcomeMessage() {
		HashMap<String, String> map = new HashMap<>();
		map.put("message", "Hello EveryOne");
		map.put("Note", "This page is for everyone");
		return map;
	}

	@GetMapping(value = "/auth")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	HashMap<String, String> welcomeMessageAuth() {
		HashMap<String, String> map = new HashMap<>();
		map.put("message", "Hello");
		map.put("Note", "This page is only for authorized");
		return map;
	}

	@GetMapping(value = "/user")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	HashMap<String, String> welcomeMessageUser() {
		HashMap<String, String> map = new HashMap<>();
		map.put("message", "Hello User");
		map.put("Note", "This page is only authorized  for users");
		return map;
	}

	@GetMapping(value = "/admin")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	HashMap<String, String> welcomeMessageAdmin() {
		HashMap<String, String> map = new HashMap<>();
		map.put("message", "Hello Admin");
		map.put("Note", "This page is only authorized to admin");
		return map;
	}

	@PostMapping("/gen")
	public String authenticateAndGetToken(@Valid @RequestBody AuthorizationRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {
			return jwtProvider.generateToken(authRequest.getUsername());
		} else {
			throw new UsernameNotFoundException("invalid user request !");
		}
	}

}
