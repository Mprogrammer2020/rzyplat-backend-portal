package com.rzyplat.service;

import com.rzyplat.dto.WeatherAlertDTO;

public interface WeatherAlertService {

	WeatherAlertDTO getCurrentWeatherAlert(String propertyName) throws Exception;
}
