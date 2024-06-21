package com.rzyplat.entity;

import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "weather_alerts")
public class WeatherAlert {

	@Id
	private String id;
	private String title;
	private String level;
	private String description;
	private String propertyName;
	private LocalDateTime alertTime;
	
	@DBRef
    private HourlyWeather weather;
	
	@CreatedDate
	private LocalDateTime createdDate;
	@CreatedBy
	private String createdBy;
	
	@LastModifiedDate
	private LocalDateTime updatedDate;
	@LastModifiedBy
	private String updatedBy;
}
