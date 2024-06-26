package com.rzyplat.impl;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.rzyplat.constant.Constants;
import com.rzyplat.entity.MoldAlertSummary;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.repository.MoldAlertSummaryRepository;
import com.rzyplat.response.MoldAlertSummaryResponse;
import com.rzyplat.service.MoldAlertSummaryService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MoldAlertSummaryServiceImpl implements MoldAlertSummaryService {

	private final ModelMapper mapper;
	private final MoldAlertSummaryRepository repository;
	
	@Override
	public MoldAlertSummaryResponse getSummary() throws EntityNotFoundException {
		List<MoldAlertSummary> alerts=repository.findAll();
		if(alerts.size()>0) {
			return mapper.map(alerts.get(0),MoldAlertSummaryResponse.class);
		}
		
		throw new EntityNotFoundException(Constants.MOLD_SUMMARY_NOT_FOUND); 
	}
}
