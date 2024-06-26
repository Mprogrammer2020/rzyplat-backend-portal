package com.rzyplat.service;

import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.response.SecurityAlertSummaryResponse;

public interface SecurityAlertSummaryService {

	SecurityAlertSummaryResponse getSummary() throws EntityNotFoundException;
}
