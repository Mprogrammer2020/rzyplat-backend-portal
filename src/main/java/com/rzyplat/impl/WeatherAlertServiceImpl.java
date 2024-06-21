package com.rzyplat.impl;

import java.time.LocalDateTime;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
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

	private final ModelMapper modelMapper;
	private final WeatherAlertRepository repository;
	
	@Override
	public WeatherAlertDTO getCurrentWeatherAlert(String propertyName,LocalDateTime startTime) throws Exception {
		WeatherAlert weatherAlert=repository.findByPropertyNameAndAlertTime(propertyName, startTime)
				.orElseThrow(() -> new EntityNotFoundException(String.format(Constants.NO_ALERTS_AT_TIME, propertyName, startTime.toString())));
		
		return modelMapper.map(weatherAlert, WeatherAlertDTO.class);
	} 
}
