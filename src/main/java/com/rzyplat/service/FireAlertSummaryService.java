package com.rzyplat.service;

import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.response.FireAlertSummaryResponse;

public interface FireAlertSummaryService {

	FireAlertSummaryResponse getSummary() throws EntityNotFoundException;
}
