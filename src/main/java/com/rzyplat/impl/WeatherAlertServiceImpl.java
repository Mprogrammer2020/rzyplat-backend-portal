package com.rzyplat.impl;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzyplat.constant.Constants;
import com.rzyplat.dto.WeatherAlertDTO;
import com.rzyplat.entity.WeatherAlert;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.repository.WeatherAlertRepository;
import com.rzyplat.service.WeatherAlertService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WeatherAlertServiceImpl implements WeatherAlertService {

	private final ObjectMapper objectMapper;
	private final WeatherAlertRepository repository;
	
	@Override
	public WeatherAlertDTO getCurrentWeatherAlert(String propertyName) throws Exception {
		LocalDateTime currentTime=LocalDateTime.of(2024, 6, 1, 1, 0);//LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
		
		WeatherAlert weatherAlert=repository.findByPropertyNameAndAlertTime(propertyName, currentTime)
				.orElseThrow(() -> new EntityNotFoundException(String.format(Constants.NO_ALERTS_AT_TIME, propertyName, currentTime.toString())));
		
		return objectMapper.convertValue(weatherAlert, WeatherAlertDTO.class);
	} 
}
