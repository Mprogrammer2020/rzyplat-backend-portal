package com.rzyplat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.rzyplat.entity.MonitoringAlertHistory;

public interface MonitoringAlertHistoryRepository extends MongoRepository<MonitoringAlertHistory, String> {

}
