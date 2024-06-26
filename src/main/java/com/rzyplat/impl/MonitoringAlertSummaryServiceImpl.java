package com.rzyplat.impl;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.rzyplat.constant.Constants;
import com.rzyplat.entity.MonitoringAlertSummary;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.repository.MonitoringAlertSummaryRepository;
import com.rzyplat.response.MonitoringAlertSummaryResponse;
import com.rzyplat.service.MonitoringAlertSummaryService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MonitoringAlertSummaryServiceImpl implements MonitoringAlertSummaryService {

	private final ModelMapper mapper;
	private final MonitoringAlertSummaryRepository repository;
	
	@Override
	public MonitoringAlertSummaryResponse getSummary() throws EntityNotFoundException {
		List<MonitoringAlertSummary> alerts=repository.findAll();
		if(alerts.size()>0) {
			return mapper.map(alerts.get(0),MonitoringAlertSummaryResponse.class);
		}
		
		throw new EntityNotFoundException(Constants.MONITORING_SUMMARY_NOT_FOUND); 
	}
}
