package com.rzyplat.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Document(collection = "hourly_weather")
public class HourlyWeather {

	@Id
	private String id;
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
	
	@CreatedDate
	private LocalDateTime createdDate;
	@CreatedBy
	private String createdBy;
	
	@LastModifiedDate
	private LocalDateTime updatedDate;
	@LastModifiedBy
	private String updatedBy;
			
}
