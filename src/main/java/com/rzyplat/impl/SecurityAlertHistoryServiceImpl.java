package com.rzyplat.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.google.gson.reflect.TypeToken;
import com.rzyplat.constant.Constants;
import com.rzyplat.dto.SecurityAlertHistoryDTO;
import com.rzyplat.entity.SecurityAlertHistory;
import com.rzyplat.repository.SecurityAlertHistoryRepository;
import com.rzyplat.response.SecurityAlertHistoryResponse;
import com.rzyplat.service.SecurityAlertHistoryService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SecurityAlertHistoryServiceImpl implements SecurityAlertHistoryService {
	
	private final ModelMapper mapper;
	private final SecurityAlertHistoryRepository repository;

	@Override
	public SecurityAlertHistoryResponse getHistory(Integer pageNumber, Integer pageSize) {
        Page<SecurityAlertHistory> paged=repository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Constants.ID).descending()));
        
        List<SecurityAlertHistoryDTO> content=mapper.map(paged.getContent(), new TypeToken<List<SecurityAlertHistoryDTO>>() {}.getType());
        return new SecurityAlertHistoryResponse(paged.getNumber(),paged.getSize(),paged.getTotalPages(),paged.getTotalElements(),content);
	}
}
