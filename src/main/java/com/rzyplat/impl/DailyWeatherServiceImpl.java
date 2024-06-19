package com.rzyplat.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzyplat.constant.Constants;
import com.rzyplat.dto.DailyWeatherDTO;
import com.rzyplat.repository.DailyWeatherRepository;
import com.rzyplat.service.DailyWeatherService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DailyWeatherServiceImpl implements DailyWeatherService {
	
	private final ObjectMapper objectMapper;
	private final DailyWeatherRepository repository;
	
	@Override
	public List<DailyWeatherDTO> get10DaysForecast(String propertyName) {
		LocalDate startDate=LocalDate.of(2024, 6, 1);//LocalDate.now();
		LocalDate endDate=startDate.plusDays(10);
		
		List<DailyWeatherDTO> weather=repository.find10DaysForecast(propertyName, startDate, endDate, Sort.by(Constants.WEATHER_TIME))
				.stream()
				.map(dailyWeather -> objectMapper.convertValue(dailyWeather, DailyWeatherDTO.class))
				.collect(Collectors.toList());
		
		return weather;
	}
	
	
	
}
