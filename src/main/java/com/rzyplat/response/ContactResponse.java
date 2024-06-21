package com.rzyplat.response;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ContactResponse {
	private String id;
	private String name;
	private String role;
	private String phone;
	private String email;
	private LocalDate joiningDate;
}
