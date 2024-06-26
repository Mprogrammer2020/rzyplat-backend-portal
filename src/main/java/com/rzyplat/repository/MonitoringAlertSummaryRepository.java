package com.rzyplat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.rzyplat.entity.MonitoringAlertSummary;

public interface MonitoringAlertSummaryRepository extends MongoRepository<MonitoringAlertSummary, String>{

}
