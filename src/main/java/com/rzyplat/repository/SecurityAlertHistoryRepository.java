package com.rzyplat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.rzyplat.entity.SecurityAlertHistory;

public interface SecurityAlertHistoryRepository extends MongoRepository<SecurityAlertHistory, String> {

}
