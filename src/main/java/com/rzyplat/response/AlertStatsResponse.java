package com.rzyplat.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertStatsResponse {
	
	private Integer completedAlerts;
	private Integer averageAlertsPerWeek;
	private Integer alertCloseTime;
	private Integer alertOpenTime;
}
