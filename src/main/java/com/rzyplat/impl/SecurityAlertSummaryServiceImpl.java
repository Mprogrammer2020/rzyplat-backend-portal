package com.rzyplat.impl;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.rzyplat.constant.Constants;
import com.rzyplat.entity.SecurityAlertSummary;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.repository.SecurityAlertSummaryRepository;
import com.rzyplat.response.SecurityAlertSummaryResponse;
import com.rzyplat.service.SecurityAlertSummaryService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SecurityAlertSummaryServiceImpl implements SecurityAlertSummaryService {

	private final ModelMapper mapper;
	private final SecurityAlertSummaryRepository repository;
	
	@Override
	public SecurityAlertSummaryResponse getSummary() throws EntityNotFoundException {
		List<SecurityAlertSummary> alerts=repository.findAll();
		if(alerts.size()>0) {
			return mapper.map(alerts.get(0),SecurityAlertSummaryResponse.class);
		}
		
		throw new EntityNotFoundException(Constants.SECURITY_SUMMARY_NOT_FOUND); 
	}
}
