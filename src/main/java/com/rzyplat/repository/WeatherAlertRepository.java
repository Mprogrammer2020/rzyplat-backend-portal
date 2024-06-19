package com.rzyplat.repository;

import java.util.Optional;
import java.time.LocalDateTime;
import com.rzyplat.entity.WeatherAlert;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WeatherAlertRepository extends MongoRepository<WeatherAlert, String> {

	Optional<WeatherAlert> findByPropertyNameAndAlertTime(String propertyName,LocalDateTime alertTime); 
}
