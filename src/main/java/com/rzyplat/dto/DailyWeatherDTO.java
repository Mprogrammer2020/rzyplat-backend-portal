package com.rzyplat.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DailyWeatherDTO {

	private String propertyName;
	private LocalDate weatherDate;
	private Integer minTempratureInFarenheit;
	private Integer maxTempratureInFarenheit;
	private String tempratureDescription;
	private Integer tempratureFeelsLike;
	private Integer airQuality;
	private Integer windSpeed;
	private Integer humidity;
}
