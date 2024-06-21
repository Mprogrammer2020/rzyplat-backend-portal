package com.rzyplat.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class HourlyWeatherDTO {
	
	private String propertyName;
	private LocalDateTime weatherTime;
	private Float tempratureCelcius;
	private Float tempratureFarenheit;
	private String tempratureDescription;
	private Float tempratureFeelsLike;
	private Integer airQuality;
	private Integer windSpeed;
	private Integer humidity;
	private Integer visibility;
	private Integer pressure;
	private Integer dewPoint;
}
