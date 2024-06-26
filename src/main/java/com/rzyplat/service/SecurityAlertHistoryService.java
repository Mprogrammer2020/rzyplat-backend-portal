package com.rzyplat.service;

import com.rzyplat.response.SecurityAlertHistoryResponse;

public interface SecurityAlertHistoryService {

	SecurityAlertHistoryResponse getHistory(Integer pageNumber, Integer pageSize);
}
