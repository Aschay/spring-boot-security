package com.securewebservice.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
	@NotBlank
	@Size(min = 5)
	@Pattern(regexp = "^[a-zA-Z0-9_-]+$")
	String username;
	@NotBlank
	String password;

}
