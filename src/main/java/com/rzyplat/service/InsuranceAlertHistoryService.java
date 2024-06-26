package com.rzyplat.service;

import com.rzyplat.response.InsuranceAlertHistoryResponse;

public interface InsuranceAlertHistoryService {

	InsuranceAlertHistoryResponse getHistory(Integer pageNumber, Integer pageSize);
	
	InsuranceAlertHistoryResponse getHistory(String alertStatus, Integer pageNumber, Integer pageSize);
}
