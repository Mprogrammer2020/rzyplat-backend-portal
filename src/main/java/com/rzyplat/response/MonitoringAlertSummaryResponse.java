package com.rzyplat.response;

import lombok.Data;

@Data
public class MonitoringAlertSummaryResponse {

	private Integer totalAlerts;
	private Integer activeAlerts;

}
