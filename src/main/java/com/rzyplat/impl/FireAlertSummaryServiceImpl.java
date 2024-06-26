package com.rzyplat.impl;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.rzyplat.constant.Constants;
import com.rzyplat.entity.FireAlertSummary;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.repository.FireAlertSummaryRepository;
import com.rzyplat.response.FireAlertSummaryResponse;
import com.rzyplat.service.FireAlertSummaryService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FireAlertSummaryServiceImpl implements FireAlertSummaryService {

	private final ModelMapper mapper;
	private final FireAlertSummaryRepository repository;
	
	@Override
	public FireAlertSummaryResponse getSummary() throws EntityNotFoundException {
		List<FireAlertSummary> alerts=repository.findAll();
		if(alerts.size()>0) {
			return mapper.map(alerts.get(0),FireAlertSummaryResponse.class);
		}
		
		throw new EntityNotFoundException(Constants.FIRE_SUMMARY_NOT_FOUND); 
	}
}
