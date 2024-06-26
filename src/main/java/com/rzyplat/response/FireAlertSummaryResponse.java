package com.rzyplat.response;

import lombok.Data;

@Data
public class FireAlertSummaryResponse {

	private Integer totalAlerts;
	private Integer activeAlerts;
	private Integer totalDevices;
}
