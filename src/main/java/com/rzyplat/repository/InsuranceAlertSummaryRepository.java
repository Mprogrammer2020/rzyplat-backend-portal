package com.rzyplat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.rzyplat.entity.InsuranceAlertSummary;

public interface InsuranceAlertSummaryRepository extends MongoRepository<InsuranceAlertSummary,String> {

}
