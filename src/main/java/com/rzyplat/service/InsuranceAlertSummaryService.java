package com.rzyplat.service;

import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.response.InsuranceAlertSummaryResponse;

public interface InsuranceAlertSummaryService {

	InsuranceAlertSummaryResponse getSummary() throws EntityNotFoundException;
}
