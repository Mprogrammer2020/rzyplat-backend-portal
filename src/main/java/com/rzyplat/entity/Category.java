package com.rzyplat.entity;

import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Document(collection = "category")
public class Category {
	@Id
	private String id;
	@CreatedDate
	private LocalDateTime createdOn;
	private String name;
	private String imagePath;
	private Integer count;

}
