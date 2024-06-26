package com.rzyplat.service;

import com.rzyplat.response.MonitoringAlertHistoryResponse;

public interface MonitoringAlertHistoryService {

	MonitoringAlertHistoryResponse getHistory(Integer pageNumber, Integer pageSize);
}
