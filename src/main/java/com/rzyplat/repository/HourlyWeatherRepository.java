package com.rzyplat.repository;

import com.rzyplat.entity.HourlyWeather;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface HourlyWeatherRepository extends MongoRepository<HourlyWeather, String>{

	public Optional<HourlyWeather> findByPropertyNameAndWeatherTime(String propertyName,LocalDateTime weatherTime);
	
	public List<HourlyWeather> findByPropertyNameInAndWeatherTime(List<String> propertyName,LocalDateTime weatherTime);
	
	@Query("{ 'propertyName': ?0, 'weatherTime' : { $gte : ?1, $lt : ?2 } }")
	public List<HourlyWeather> find24HoursForecast(String propertyName,LocalDateTime startDate,LocalDateTime endDate,Sort sort);
}
