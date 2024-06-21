package com.rzyplat.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "daily_weather")
public class DailyWeather {

	@Id
	private String id;
	private String propertyName;
	private LocalDate weatherDate;
	private Float minTempratureInFarenheit;
	private Float maxTempratureInFarenheit;
	private String tempratureDescription;
	private Float tempratureFeelsLike;
	private Integer airQuality;
	private Integer windSpeed;
	private Integer humidity;
	
	@CreatedDate
	private LocalDateTime createdDate;
	@CreatedBy
	private String createdBy;
	
	@LastModifiedDate
	private LocalDateTime updatedDate;
	@LastModifiedBy
	private String updatedBy;
}
