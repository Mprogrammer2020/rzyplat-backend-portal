package com.rzyplat.impl;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.google.gson.reflect.TypeToken;
import com.rzyplat.constant.Constants;
import com.rzyplat.dto.InsuranceAlertHistoryDTO;
import com.rzyplat.entity.InsuranceAlertHistory;
import com.rzyplat.repository.InsuranceAlertHistoryRepository;
import com.rzyplat.response.InsuranceAlertHistoryResponse;
import com.rzyplat.service.InsuranceAlertHistoryService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InsuranceAlertHistoryServiceImpl implements InsuranceAlertHistoryService {

	private final ModelMapper mapper;
	private final InsuranceAlertHistoryRepository repository;

	@Override
	public InsuranceAlertHistoryResponse getHistory(Integer pageNumber, Integer pageSize) {
        Page<InsuranceAlertHistory> paged=repository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Constants.ID).descending()));
        
        List<InsuranceAlertHistoryDTO> content=mapper.map(paged.getContent(), new TypeToken<List<InsuranceAlertHistoryDTO>>() {}.getType());
        return new InsuranceAlertHistoryResponse(paged.getNumber(),paged.getSize(),paged.getTotalPages(),paged.getTotalElements(),content);
	}

	@Override
	public InsuranceAlertHistoryResponse getHistory(String alertStatus, Integer pageNumber, Integer pageSize) {
		Page<InsuranceAlertHistory> paged=repository.findByAlertStatus(alertStatus, PageRequest.of(pageNumber, pageSize, Sort.by(Constants.ID).descending()));
        
		List<InsuranceAlertHistoryDTO> content=mapper.map(paged.getContent(), new TypeToken<List<InsuranceAlertHistoryDTO>>() {}.getType());
		return new InsuranceAlertHistoryResponse(paged.getNumber(),paged.getSize(),paged.getTotalPages(),paged.getTotalElements(),content);
	}
}
