package com.rzyplat.service;

import com.rzyplat.response.MonitoringDeviceResponse;

public interface MonitoringDeviceService {
	
	MonitoringDeviceResponse filter(Integer pageNumber, Integer pageSize, Boolean online,Boolean lowBattery);
}
