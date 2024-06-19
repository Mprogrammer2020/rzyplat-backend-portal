package com.rzyplat.service;

import java.util.List;
import com.rzyplat.dto.HourlyWeatherDTO;

public interface HourlyWeatherService {

	HourlyWeatherDTO getCurrentWeather(String propertyName) throws Exception;
	List<HourlyWeatherDTO> getCurrentWeatherForProperties() throws Exception;
	List<HourlyWeatherDTO> get24HoursForecast(String propertyName);
}
