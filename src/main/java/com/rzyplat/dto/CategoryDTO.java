package com.rzyplat.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryDTO {

	private String id;
	private String name;
	private String imageContentType;
	private byte[] imageContent;
	private Integer count;
	private LocalDateTime createdDate;
	
	private List<DeviceTypeDTO> deviceTypes;	
}
