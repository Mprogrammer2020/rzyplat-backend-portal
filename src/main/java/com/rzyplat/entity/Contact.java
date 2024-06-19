package com.rzyplat.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Document(collection = "contacts")
public class Contact {

	@Id
	private String id;
	private String name;
	private String role;
	private String phone;
	private String email;
	private LocalDate joiningDate;
	
	@CreatedDate
	private LocalDateTime createdDate;
	private String createdBy;
	
	@LastModifiedDate
	private LocalDateTime updatedDate;
	private String updatedBy;
}
