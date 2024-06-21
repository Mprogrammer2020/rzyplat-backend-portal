package com.rzyplat.impl;

import java.util.List;
import java.time.LocalDateTime;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
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

	private final ModelMapper modelMapper;
	private final HourlyWeatherRepository repository;
	
	@Override
	public HourlyWeatherDTO getCurrentWeather(String propertyName,LocalDateTime startTime) throws Exception {
		HourlyWeather hourlyWeather=repository.findByPropertyNameAndWeatherTime(propertyName,startTime)
				.orElseThrow(() -> new EntityNotFoundException(String.format(Constants.NO_HOURLY_DATA_AT_TIME, propertyName,String.valueOf(startTime))));
		
		return modelMapper.map(hourlyWeather, HourlyWeatherDTO.class);
	}
	
	@Override
	public List<HourlyWeatherDTO> getCurrentWeatherForProperties(LocalDateTime startTime) throws Exception {
		List<String> properties=List.of(Constants.DEF_PROPERTY,Constants.PROPERTY_1,Constants.PROPERTY_2,Constants.PROPERTY_3);
		
		List<HourlyWeather> weather=repository.findByPropertyNameInAndWeatherTime(properties,startTime);
		return modelMapper.map(weather, List.class);
	}
	
	@Override
	public List<HourlyWeatherDTO> get24HoursForecast(String propertyName,LocalDateTime startTime) {
		LocalDateTime endTime=startTime.plusHours(24);

		List<HourlyWeather> list=repository.findByPropertyNameAndWeatherTimeBetween(propertyName, startTime, endTime, Sort.by(Constants.WEATHER_TIME));
		return modelMapper.map(list, List.class);
	}
}
