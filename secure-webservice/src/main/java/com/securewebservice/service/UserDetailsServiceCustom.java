package com.securewebservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.securewebservice.model.UserApp;
import com.securewebservice.model.UserDetailsCustom;
import com.securewebservice.repo.UserRepository;

@Service
public class UserDetailsServiceCustom implements UserDetailsService {

	@Autowired
	public UserRepository repository;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<UserApp> userDetail = repository.findByUsername(username);
		return userDetail.map(UserDetailsCustom::new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
	}



	
}