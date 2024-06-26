package com.rzyplat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.response.MonitoringDeviceResponse;
import com.rzyplat.service.MonitoringDeviceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/monitoring/devices")
public class MonitoringDeviceController {

	private final MonitoringDeviceService monitoringDeviceService;
	
	@GetMapping
	public ResponseEntity<MonitoringDeviceResponse> filter(
			@RequestParam(defaultValue = "0", required=false) Integer pageNumber,
			@RequestParam(defaultValue = "10", required=false) Integer pageSize,
			@RequestParam(required=false) Boolean online,
			@RequestParam(required=false) Boolean lowBattery) throws EntityNotFoundException {
		log.info("filter started at {}", System.currentTimeMillis());
		MonitoringDeviceResponse monitoringDevicesResponse=monitoringDeviceService.filter(pageNumber, pageSize, online, lowBattery);
		log.info("filter finished at {}", System.currentTimeMillis());
		return new ResponseEntity<>(monitoringDevicesResponse, HttpStatus.OK);
	}
}
