package com.rzyplat.request;

import lombok.Data;
import com.rzyplat.constant.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class UpdateContactRequest {
	@NotBlank(message = Constants.VALIDATION_NAME)
	private String name;
	@NotBlank(message = Constants.VALIDATION_ROLE)
	private String role;
	@NotBlank(message = Constants.VALIDATION_PHONE)
	private String phone;
	@Email(message = Constants.VALIDATION_EMAIL_FORMAT)
	@NotBlank(message = Constants.VALIDATION_EMAIL)
	private String email;
}
