package com.rzyplat.impl;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.rzyplat.constant.Constants;
import com.rzyplat.entity.AlertStats;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.repository.AlertStatsRepository;
import com.rzyplat.response.AlertStatsResponse;
import com.rzyplat.service.AlertStatsService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AlertStatsServiceImpl implements AlertStatsService {

	private final ModelMapper mapper;
	private final AlertStatsRepository repository;
	
	@Override
	public AlertStatsResponse getStats() throws EntityNotFoundException {
		List<AlertStats> alerts=repository.findAll();
		if(alerts.size()>0) {
			return mapper.map(alerts.get(0),AlertStatsResponse.class);
		}
		
		throw new EntityNotFoundException(Constants.ALERT_STATS_NOT_FOUND); 
	}
}
