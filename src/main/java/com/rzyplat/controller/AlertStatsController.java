package com.rzyplat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.response.AlertStatsResponse;
import com.rzyplat.service.AlertStatsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/alerts/stats")
public class AlertStatsController {

	private final AlertStatsService alertStatsService;
	
	@GetMapping
	public ResponseEntity<AlertStatsResponse> getStats() throws EntityNotFoundException {
		log.info("getStats started at {}", System.currentTimeMillis());
		AlertStatsResponse alertStatResponse=alertStatsService.getStats();
		log.info("getStats finished at {}", System.currentTimeMillis());
		return new ResponseEntity<>(alertStatResponse, HttpStatus.OK);
	}
}
