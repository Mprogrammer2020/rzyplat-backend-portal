package com.rzyplat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rzyplat.entity.AlertStats;

public interface AlertStatsRepository extends MongoRepository<AlertStats, String>{

}
