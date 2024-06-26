package com.rzyplat.repository;

import com.rzyplat.entity.FireAlertHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FireAlertHistoryRepository extends MongoRepository<FireAlertHistory, String> {

}
