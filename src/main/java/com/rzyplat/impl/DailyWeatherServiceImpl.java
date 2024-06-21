package com.rzyplat.impl;

import java.time.LocalDate;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.rzyplat.constant.Constants;
import com.rzyplat.dto.DailyWeatherDTO;
import com.rzyplat.entity.DailyWeather;
import com.rzyplat.repository.DailyWeatherRepository;
import com.rzyplat.service.DailyWeatherService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DailyWeatherServiceImpl implements DailyWeatherService {
	
	private final ModelMapper modelMapper;
	private final DailyWeatherRepository repository;
	
	@Override
	public List<DailyWeatherDTO> get10DaysForecast(String propertyName,LocalDate startDate) {
		LocalDate endDate=startDate.plusDays(10);
		
		List<DailyWeather> weather=repository.findByPropertyNameAndWeatherDateBetween(propertyName, startDate, endDate, Sort.by(Constants.WEATHER_DATE));
		return modelMapper.map(weather, List.class);
	}
	
	
	
}
