package com.securewebservice.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.securewebservice.model.UserApp;

@Repository
public interface UserRepository extends JpaRepository<UserApp, Integer> {

	Optional<UserApp> findByUsername(String username);
	Boolean existsByUsername(String username);

}
