package com.rzyplat.service;

import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.response.AlertStatsResponse;

public interface AlertStatsService {

	AlertStatsResponse getStats() throws EntityNotFoundException;
}
