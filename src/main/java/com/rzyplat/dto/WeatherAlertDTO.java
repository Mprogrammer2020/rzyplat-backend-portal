package com.rzyplat.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WeatherAlertDTO {

	private String title;
	private String level;
	private String description;
	private LocalDateTime alertTime;
	private HourlyWeatherDTO weather;
	
}
