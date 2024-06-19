package com.rzyplat.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.rzyplat.constant.Constants;
import com.rzyplat.dto.DailyWeatherDTO;
import com.rzyplat.service.DailyWeatherService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/weather/daily")
public class DailyWeatherController {
	
	private final DailyWeatherService service;

	@GetMapping
	public ResponseEntity<List<DailyWeatherDTO>> get10DaysForecast(
			@RequestParam(required = false, defaultValue = Constants.DEF_PROPERTY) String propertyName) {
		log.info("get10DaysForecast started at {}", System.currentTimeMillis());
		List<DailyWeatherDTO> forecast=service.get10DaysForecast(propertyName);
		log.info("get10DaysForecast finished at {}", System.currentTimeMillis());
		return new ResponseEntity<>(forecast, HttpStatus.OK);
	}
	

}
