package com.rzyplat.service;

import com.rzyplat.response.FireAlertHistoryResponse;

public interface FireAlertHistoryService {

	FireAlertHistoryResponse getHistory(Integer pageNumber, Integer pageSize);
}
