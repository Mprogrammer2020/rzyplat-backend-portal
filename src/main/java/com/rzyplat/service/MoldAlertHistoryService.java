package com.rzyplat.service;

import com.rzyplat.response.MoldAlertHistoryResponse;

public interface MoldAlertHistoryService {

	MoldAlertHistoryResponse getHistory(Integer pageNumber, Integer pageSize);
}
