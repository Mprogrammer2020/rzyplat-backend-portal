package com.rzyplat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.rzyplat.entity.MoldAlertHistory;

public interface MoldAlertHistoryRepository extends MongoRepository<MoldAlertHistory, String> {

}
