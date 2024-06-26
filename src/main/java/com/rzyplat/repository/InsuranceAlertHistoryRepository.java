package com.rzyplat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.rzyplat.entity.InsuranceAlertHistory;

public interface InsuranceAlertHistoryRepository extends MongoRepository<InsuranceAlertHistory, String> {

	Page<InsuranceAlertHistory> findByAlertStatus(String alertStatus,Pageable pageable);
}
