package com.rzyplat.service;

import java.time.LocalDate;
import java.util.List;
import com.rzyplat.dto.DailyWeatherDTO;

public interface DailyWeatherService {
	
	List<DailyWeatherDTO> get10DaysForecast(String propertyName,LocalDate startDate);
}
