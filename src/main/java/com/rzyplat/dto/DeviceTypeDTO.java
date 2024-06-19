package com.rzyplat.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceTypeDTO {

	private String id;
	private String type;
	private String imageContentType;
	private byte[] imageContent;
	private Integer count;
	private LocalDateTime createdDate;
}
