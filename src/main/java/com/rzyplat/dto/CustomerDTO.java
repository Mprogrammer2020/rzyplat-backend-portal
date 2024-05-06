package com.rzyplat.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CustomerDTO {

	private String id;
	private String name;
	private String role;
	private String phone;
	private String email;
	private List<String> property;
	private LocalDateTime createdDate;
	
}
