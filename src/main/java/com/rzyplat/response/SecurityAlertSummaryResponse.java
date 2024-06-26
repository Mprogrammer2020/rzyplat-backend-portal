package com.rzyplat.response;

import lombok.Data;

@Data
public class SecurityAlertSummaryResponse {

	private Integer totalAlerts;
	private Integer activeAlerts;
}
