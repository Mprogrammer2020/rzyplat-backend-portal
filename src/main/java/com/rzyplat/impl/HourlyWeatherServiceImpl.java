package com.rzyplat.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzyplat.constant.Constants;
import com.rzyplat.dto.HourlyWeatherDTO;
import com.rzyplat.entity.HourlyWeather;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.repository.HourlyWeatherRepository;
import com.rzyplat.service.HourlyWeatherService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HourlyWeatherServiceImpl implements HourlyWeatherService {

	private final ObjectMapper objectMapper;
	private final HourlyWeatherRepository repository;
	
	@Override
	public HourlyWeatherDTO getCurrentWeather(String propertyName) throws Exception {
		LocalDateTime currentTime=LocalDateTime.of(2024, 6, 1, 0, 0);//LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
		
		HourlyWeather hourlyWeather=repository.findByPropertyNameAndWeatherTime(propertyName,currentTime)
				.orElseThrow(() -> new EntityNotFoundException(String.format(Constants.NO_HOURLY_DATA_AT_TIME, propertyName,currentTime.toString())));
		
		return objectMapper.convertValue(hourlyWeather, HourlyWeatherDTO.class);
	}
	
	@Override
	public List<HourlyWeatherDTO> getCurrentWeatherForProperties() throws Exception {
		LocalDateTime currentTime=LocalDateTime.of(2024, 6, 1, 0, 0);//LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
		
		List<String> properties=List.of(Constants.DEF_PROPERTY,Constants.PROPERTY_1,Constants.PROPERTY_2,Constants.PROPERTY_3);
		
		return repository.findByPropertyNameInAndWeatherTime(properties,currentTime)
				.stream()
				.map(hourlyWeather -> objectMapper.convertValue(hourlyWeather, HourlyWeatherDTO.class))
				.collect(Collectors.toList());
	}
	
	@Override
	public List<HourlyWeatherDTO> get24HoursForecast(String propertyName) {
		LocalDateTime startDate=LocalDateTime.of(2024, 6, 1, 0, 0);//LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
		LocalDateTime endDate=startDate.plusHours(24);
		
		List<HourlyWeatherDTO> weather=repository.find24HoursForecast(propertyName, startDate, endDate, Sort.by(Constants.WEATHER_TIME))
				.stream()
				.map(dailyWeather -> objectMapper.convertValue(dailyWeather, HourlyWeatherDTO.class))
				.collect(Collectors.toList());
		
		return weather;
	}
}
