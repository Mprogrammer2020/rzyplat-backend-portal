package com.rzyplat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.rzyplat.response.MonitoringAlertHistoryResponse;
import com.rzyplat.service.MonitoringAlertHistoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/alerts/monitoring/history")
public class MonitoringAlertHistoryController {

	private final MonitoringAlertHistoryService alertHistoryService;
	
	@GetMapping
	public ResponseEntity<MonitoringAlertHistoryResponse> getHistory(
			@RequestParam(defaultValue = "0", required=false) Integer pageNumber,
			@RequestParam(defaultValue = "10", required=false) Integer pageSize) {
		log.info("getHistory started at {}", System.currentTimeMillis());
		MonitoringAlertHistoryResponse history=alertHistoryService.getHistory(pageNumber, pageSize);
		log.info("getHistory finished at {}", System.currentTimeMillis());
		return new ResponseEntity<>(history, HttpStatus.OK);
	}
}
