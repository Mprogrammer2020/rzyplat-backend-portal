package com.rzyplat.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class WeatherAlertDTO {
	
	private String title;
	private String level;
	private String description;
	private LocalDateTime alertTime;
	private HourlyWeatherDTO weather;
}
