package com.rzyplat.service;

import java.time.LocalDateTime;
import java.util.List;
import com.rzyplat.dto.HourlyWeatherDTO;

public interface HourlyWeatherService {

	HourlyWeatherDTO getCurrentWeather(String propertyName,LocalDateTime startTime) throws Exception;
	List<HourlyWeatherDTO> getCurrentWeatherForProperties(LocalDateTime startTime) throws Exception;
	List<HourlyWeatherDTO> get24HoursForecast(String propertyName,LocalDateTime startTime);
}
