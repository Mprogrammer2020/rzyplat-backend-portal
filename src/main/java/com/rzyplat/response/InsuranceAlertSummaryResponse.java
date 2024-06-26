package com.rzyplat.response;

import lombok.Data;

@Data
public class InsuranceAlertSummaryResponse {

	private Integer totalAlerts;
	private Integer activeAlerts;
	private Integer totalClaims;
	private Integer severeAlerts;
}
