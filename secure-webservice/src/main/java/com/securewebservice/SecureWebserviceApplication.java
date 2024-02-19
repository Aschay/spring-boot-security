package com.securewebservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class SecureWebserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecureWebserviceApplication.class, args);
	}

}
