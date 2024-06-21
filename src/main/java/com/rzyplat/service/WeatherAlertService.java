package com.rzyplat.service;

import java.time.LocalDateTime;
import com.rzyplat.dto.WeatherAlertDTO;

public interface WeatherAlertService {

	WeatherAlertDTO getCurrentWeatherAlert(String propertyName,LocalDateTime startTime) throws Exception;
}
