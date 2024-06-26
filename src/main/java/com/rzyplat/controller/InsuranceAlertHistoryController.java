package com.rzyplat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rzyplat.constant.Constants;
import com.rzyplat.response.InsuranceAlertHistoryResponse;
import com.rzyplat.service.InsuranceAlertHistoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/alerts/insurance/history")
public class InsuranceAlertHistoryController {

	private final InsuranceAlertHistoryService alertHistoryService;
	
	@GetMapping
	public ResponseEntity<InsuranceAlertHistoryResponse> getHistory(
			@RequestParam(defaultValue = "0", required=false) Integer pageNumber,
			@RequestParam(defaultValue = "10", required=false) Integer pageSize)  {
		log.info("getHistory started at {}", System.currentTimeMillis());
		InsuranceAlertHistoryResponse history=alertHistoryService.getHistory(pageNumber, pageSize);
		log.info("getHistory finished at {}", System.currentTimeMillis());
		return new ResponseEntity<>(history, HttpStatus.OK);
	}
	
	@GetMapping("/severe")
	public ResponseEntity<InsuranceAlertHistoryResponse> getSevereAlerts(
			@RequestParam(defaultValue = "0", required=false) Integer pageNumber,
			@RequestParam(defaultValue = "10", required=false) Integer pageSize)  {
		log.info("getSevereAlerts started at {}", System.currentTimeMillis());
		InsuranceAlertHistoryResponse history=alertHistoryService.getHistory(Constants.SEVERE_ALERT, pageNumber, pageSize);
		log.info("getSevereAlerts finished at {}", System.currentTimeMillis());
		return new ResponseEntity<>(history, HttpStatus.OK);
	}
}
