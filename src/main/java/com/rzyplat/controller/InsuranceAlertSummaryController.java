package com.rzyplat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.response.InsuranceAlertSummaryResponse;
import com.rzyplat.service.InsuranceAlertSummaryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/alerts/insurance")
public class InsuranceAlertSummaryController {

	private final InsuranceAlertSummaryService alertSummaryService;
	
	@GetMapping
	public ResponseEntity<InsuranceAlertSummaryResponse> getStats() throws EntityNotFoundException {
		log.info("getStats started at {}", System.currentTimeMillis());
		InsuranceAlertSummaryResponse summary=alertSummaryService.getSummary();
		log.info("getStats finished at {}", System.currentTimeMillis());
		return new ResponseEntity<>(summary, HttpStatus.OK);
	}
}
