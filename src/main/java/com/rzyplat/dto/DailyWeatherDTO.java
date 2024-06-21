package com.rzyplat.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class DailyWeatherDTO {

	private String propertyName;
	private LocalDate weatherDate;
	private Float minTempratureInFarenheit;
	private Float maxTempratureInFarenheit;
	private String tempratureDescription;
	private Float tempratureFeelsLike;
	private Integer airQuality;
	private Integer windSpeed;
	private Integer humidity;
}
