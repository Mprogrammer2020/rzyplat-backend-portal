package com.rzyplat.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class MonitoringDeviceDTO {

	private String deviceId;
	private String property;
	private LocalDate installationDate;
	private Float temperature;
	private Float humidity;

	private Integer batteryLevel;
	private Boolean online;
}
