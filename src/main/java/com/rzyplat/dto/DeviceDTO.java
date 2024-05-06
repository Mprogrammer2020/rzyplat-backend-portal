package com.rzyplat.dto;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DeviceDTO {

	@Id
	private String id;
	
    private String categoryId;
    private String categoryName;
    
    private String deviceTypeId;
    private String deviceTypeLabel;
    
	private String serialNumber;
	private String sku;
	private String manufacturer;

	private LocalDateTime createdDate;

}
