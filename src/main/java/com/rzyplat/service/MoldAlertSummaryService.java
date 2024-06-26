package com.rzyplat.service;

import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.response.MoldAlertSummaryResponse;

public interface MoldAlertSummaryService {

	MoldAlertSummaryResponse getSummary() throws EntityNotFoundException;
}
