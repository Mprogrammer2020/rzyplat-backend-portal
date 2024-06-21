package com.rzyplat.repository;

import java.util.List;
import java.time.LocalDate;
import com.rzyplat.entity.DailyWeather;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DailyWeatherRepository extends MongoRepository<DailyWeather, String> {

	public List<DailyWeather> findByPropertyNameAndWeatherDateBetween(String propertyName,LocalDate startDate,LocalDate endDate,Sort sort);
}
