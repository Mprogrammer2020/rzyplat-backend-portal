package com.rzyplat.entity;

import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Document(collection = "deviceType")
public class DeviceType {
	@Id
	private String id;
	@CreatedDate
	private LocalDateTime createdOn;
	private String type;
	private String imagePath;
	private Integer count;
	@DBRef
    @JsonIgnore
    private Category category;
	

}
