package com.securewebservice.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="users",uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
            })
       
    })	
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserApp {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	
	@NotBlank
	@Size(min =5)
	private String username;
	
	
	@NotBlank
	@Email
	@Pattern(regexp = "^(.+)@(.+)$")
	@Size(min =5)
	private String email;
	
	
	@NotBlank
    private String password;
	@NotBlank
	private String roles ;
}
