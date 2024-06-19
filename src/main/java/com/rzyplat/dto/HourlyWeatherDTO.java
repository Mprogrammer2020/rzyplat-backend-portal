package com.rzyplat.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HourlyWeatherDTO {

	private String propertyName;
	private LocalDateTime weatherTime;
	private Integer tempratureCelcius;
	private Integer tempratureFarenheit;
	private String tempratureDescription;
	private Integer tempratureFeelsLike;
	private Integer airQuality;
	private Integer windSpeed;
	private Integer humidity;
	private Integer visibility;
	private Integer pressure;
	private Integer dewPoint;
}
