package com.rzyplat.request;

import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

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
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<String> getProperty() {
		return property;
	}
	public void setProperty(List<String> property) {
		this.property = property;
	}
}
