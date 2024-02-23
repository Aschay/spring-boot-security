package com.securewebservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.securewebservice.model.UserApp;
import com.securewebservice.repo.UserRepository;

@Service
public class UserAppService {

	@Autowired
	private UserRepository userRepository;

	public Optional<UserApp> getByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Autowired
	public PasswordEncoder encoderService;

	public void addUser(UserApp userInfo) {
		userInfo.setPassword(encoderService.encode(userInfo.getPassword()));
		userRepository.save(userInfo);

	}

	public Boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

}
