package com.rzyplat.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter @Setter
@Document(collection = "devices")
public class Device {
	@Id
	private String id;
	
    @DBRef
    @JsonIgnore
    private Category category;
    
    @DBRef
    private DeviceType deviceType;
    
	private String serialNumber;
	private String sku;
	private String manufacturer;
	@CreatedDate
	private LocalDateTime createdDate;

}
