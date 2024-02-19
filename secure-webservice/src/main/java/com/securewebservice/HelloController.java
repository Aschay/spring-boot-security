package com.securewebservice;

import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping(value = "/")
	HashMap<String, String> welcomeMessage() {
		HashMap<String, String> map = new HashMap<>();
		map.put("message", "Hello EveryOne");
		map.put("Note", "This page is for everyone");
		return map;
	}
	@GetMapping(value = "/auth")
	HashMap<String, String> welcomeMessageAuth() {
		HashMap<String, String> map = new HashMap<>();
		map.put("message", "Hello User");
		map.put("Note", "This page is only for authorized");
		return map;
	}
	@GetMapping(value = "/user")
	HashMap<String, String> welcomeMessageUser() {
		HashMap<String, String> map = new HashMap<>();
		map.put("message", "Hello User");
		map.put("Note", "This page is only for users");
		return map;
	}
	
	@GetMapping(value = "/admin")
	HashMap<String, String> welcomeMessageAdmin() {
		HashMap<String, String> map = new HashMap<>();
		map.put("message", "Hello Admin");
		map.put("Note", "This page is only accessible to admin");
		return map;
	}
	

}
