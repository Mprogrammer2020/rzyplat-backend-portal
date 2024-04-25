package com.rzyplat.request;

import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

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
