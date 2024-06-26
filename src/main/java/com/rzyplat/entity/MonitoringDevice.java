package com.rzyplat.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "monitoring_devices")
public class MonitoringDevice {

	@Id
	private String id;
	private String deviceId;
	private String property;
	private LocalDate installationDate;
	private Float temperature;
	private Float humidity;

	private Integer batteryLevel;
	private Boolean online;
	
	@CreatedDate
	private LocalDateTime createdDate;
	private String createdBy;
	
	@LastModifiedDate
	private LocalDateTime updatedDate;
	private String updatedBy;
}
