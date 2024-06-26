package com.rzyplat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.rzyplat.entity.FireAlertSummary;

public interface FireAlertSummaryRepository extends MongoRepository<FireAlertSummary, String> {

}
