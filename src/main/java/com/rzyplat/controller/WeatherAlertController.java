package com.rzyplat.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.rzyplat.constant.Constants;
import com.rzyplat.dto.WeatherAlertDTO;
import com.rzyplat.service.WeatherAlertService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/weather/alerts")
public class WeatherAlertController {

	private final WeatherAlertService service;

	@GetMapping
	public ResponseEntity<WeatherAlertDTO> getCurrentWeatherAlert(
			@RequestParam(required = false, defaultValue = Constants.DEF_PROPERTY) String propertyName,
			@RequestParam(required = false, defaultValue = Constants.DEF_DATETIME) LocalDateTime startTime) throws Exception {
		log.info("getCurrentWeatherAlert started at {}", System.currentTimeMillis());
		WeatherAlertDTO alert=service.getCurrentWeatherAlert(propertyName,startTime);
		log.info("getCurrentWeatherAlert finished at {}", System.currentTimeMillis());
		return new ResponseEntity<>(alert, HttpStatus.OK);
	}
}
