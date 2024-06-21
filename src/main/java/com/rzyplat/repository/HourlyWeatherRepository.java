package com.rzyplat.repository;

import com.rzyplat.entity.HourlyWeather;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HourlyWeatherRepository extends MongoRepository<HourlyWeather, String>{

	public Optional<HourlyWeather> findByPropertyNameAndWeatherTime(String propertyName,LocalDateTime weatherTime);
	public List<HourlyWeather> findByPropertyNameInAndWeatherTime(List<String> propertyName,LocalDateTime weatherTime);
	public List<HourlyWeather> findByPropertyNameAndWeatherTimeBetween(String propertyName,LocalDateTime startDate,LocalDateTime endDate,Sort sort);
}
