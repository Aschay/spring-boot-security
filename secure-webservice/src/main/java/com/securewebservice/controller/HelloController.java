package com.securewebservice.controller;

import java.util.HashMap;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class HelloController {



	@GetMapping(value = "/home")
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

}
