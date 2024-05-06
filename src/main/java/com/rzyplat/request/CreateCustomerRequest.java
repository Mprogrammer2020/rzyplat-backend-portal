package com.rzyplat.request;

import java.util.List;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateCustomerRequest {

	@NotBlank
	private String name;
	@NotBlank
	private String role;
	@NotBlank
	private String phone;
	@Email
	private String email;
	@NotEmpty
	private List<String> property;
	
}
