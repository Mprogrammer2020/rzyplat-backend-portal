package com.rzyplat.impl;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.rzyplat.constant.Constants;
import com.rzyplat.entity.InsuranceAlertSummary;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.repository.InsuranceAlertSummaryRepository;
import com.rzyplat.response.InsuranceAlertSummaryResponse;
import com.rzyplat.service.InsuranceAlertSummaryService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InsuranceAlertSummaryServiceImpl implements InsuranceAlertSummaryService {

	private final ModelMapper mapper;
	private final InsuranceAlertSummaryRepository repository;
	
	@Override
	public InsuranceAlertSummaryResponse getSummary() throws EntityNotFoundException {
		List<InsuranceAlertSummary> alerts=repository.findAll();
		if(alerts.size()>0) {
			return mapper.map(alerts.get(0),InsuranceAlertSummaryResponse.class);
		}
		
		throw new EntityNotFoundException(Constants.INSURANCE_SUMMARY_NOT_FOUND); 
	}
}
