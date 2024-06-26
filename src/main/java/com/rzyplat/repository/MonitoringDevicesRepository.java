package com.rzyplat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.rzyplat.entity.MonitoringDevice;

public interface MonitoringDevicesRepository extends MongoRepository<MonitoringDevice, String> {

	Integer countByOnlineIsTrue();
	Integer countByOnlineIsFalse();
	Integer countByBatteryLevelLessThan(Integer batteryLevel);
}
