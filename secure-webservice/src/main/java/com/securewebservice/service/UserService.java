package com.securewebservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.securewebservice.model.UserApp;
import com.securewebservice.repo.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public Optional<UserApp> getByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}
