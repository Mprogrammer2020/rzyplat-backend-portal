package com.rzyplat.service;

import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.response.MonitoringAlertSummaryResponse;

public interface MonitoringAlertSummaryService {

	MonitoringAlertSummaryResponse getSummary() throws EntityNotFoundException;
}
