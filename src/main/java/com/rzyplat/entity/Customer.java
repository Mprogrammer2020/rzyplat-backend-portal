package com.rzyplat.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Document(collection = "customers")
public class Customer {
	
	@Id
	private String id;
	private String name;
	private String role;
	@CreatedDate
	private LocalDateTime createdDate;
	private String phone;
	private String email;
	private List<String> property;
	
}
