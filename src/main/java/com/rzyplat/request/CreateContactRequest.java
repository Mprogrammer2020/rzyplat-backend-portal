package com.rzyplat.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateContactRequest {

	@NotBlank
	private String name;
	@NotBlank
	private String role;
	@NotBlank
	private String phone;
	@NotBlank
	private String email;
	
}
