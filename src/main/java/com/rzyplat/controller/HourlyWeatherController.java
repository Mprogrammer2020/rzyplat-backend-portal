package com.rzyplat.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.rzyplat.constant.Constants;
import com.rzyplat.dto.HourlyWeatherDTO;
import com.rzyplat.impl.HourlyWeatherServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/weather/hourly")
public class HourlyWeatherController {
	
	private final HourlyWeatherServiceImpl service;

	@GetMapping
	public ResponseEntity<List<HourlyWeatherDTO>> get24HoursForecast(
			@RequestParam(required = false, defaultValue = Constants.DEF_PROPERTY) String propertyName,
			@RequestParam(required = false, defaultValue = Constants.DEF_DATETIME) LocalDateTime startTime) {
		log.info("get24HoursForecast started at {}", System.currentTimeMillis());
		List<HourlyWeatherDTO> forecast=service.get24HoursForecast(propertyName,startTime);
		log.info("get24HoursForecast finished at {}", System.currentTimeMillis());
		return new ResponseEntity<>(forecast, HttpStatus.OK);
	}
	
	@GetMapping("/current")
	public ResponseEntity<HourlyWeatherDTO> getCurrentWeather(
			@RequestParam(required = false, defaultValue = Constants.DEF_PROPERTY) String propertyName,
			@RequestParam(required = false, defaultValue = Constants.DEF_DATETIME) LocalDateTime startTime) throws Exception {
		log.info("getCurrentWeather started at {}", System.currentTimeMillis());
		HourlyWeatherDTO hourlyWeather=service.getCurrentWeather(propertyName,startTime);
		log.info("getCurrentWeather finished at {}", System.currentTimeMillis());
		return new ResponseEntity<>(hourlyWeather, HttpStatus.OK);
	}
	
	@GetMapping("/current/properties")
	public ResponseEntity<List<HourlyWeatherDTO>> getCurrentWeatherForProperties(
			@RequestParam(required = false, defaultValue = Constants.DEF_DATETIME) LocalDateTime startTime) throws Exception {
		log.info("getCurrentWeatherForProperties started at {}", System.currentTimeMillis());
		List<HourlyWeatherDTO> hourlyWeather=service.getCurrentWeatherForProperties(startTime);
		log.info("getCurrentWeatherForProperties finished at {}", System.currentTimeMillis());
		return new ResponseEntity<>(hourlyWeather, HttpStatus.OK);
	}
}
