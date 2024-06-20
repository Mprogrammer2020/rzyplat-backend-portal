package com.rzyplat.repository;

import com.rzyplat.entity.DailyWeather;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface DailyWeatherRepository extends MongoRepository<DailyWeather, String> {

	@Query("{ 'propertyName': ?0, 'weatherDate' : { $gte : ?1, $lt : ?2 } }")
	public List<DailyWeather> find10DaysForecast(String propertyName,LocalDate startDate,LocalDate endDate,Sort sort);
}
