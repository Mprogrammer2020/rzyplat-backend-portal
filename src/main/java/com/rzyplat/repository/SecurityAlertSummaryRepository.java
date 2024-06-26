package com.rzyplat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.rzyplat.entity.SecurityAlertSummary;

public interface SecurityAlertSummaryRepository extends MongoRepository<SecurityAlertSummary,String>{

}
