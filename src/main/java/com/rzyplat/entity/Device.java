package com.rzyplat.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Document(collection = "devices")
public class Device {
	@Id
	private String id;
	
    @DBRef
    private Category category;
    
    @DBRef
    private DeviceType deviceType;
    
	private String serialNumber;
	private String sku;
	private String manufacturer;

	@CreatedDate
	private LocalDateTime createdDate;
	private String createdBy;
	
	@LastModifiedDate
	private LocalDateTime updatedDate;
	private String updatedBy;

}
