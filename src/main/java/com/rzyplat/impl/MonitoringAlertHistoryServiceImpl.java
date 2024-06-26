package com.rzyplat.impl;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.google.gson.reflect.TypeToken;
import com.rzyplat.constant.Constants;
import com.rzyplat.dto.MonitoringAlertHistoryDTO;
import com.rzyplat.entity.MonitoringAlertHistory;
import com.rzyplat.repository.MonitoringAlertHistoryRepository;
import com.rzyplat.response.MonitoringAlertHistoryResponse;
import com.rzyplat.service.MonitoringAlertHistoryService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MonitoringAlertHistoryServiceImpl implements MonitoringAlertHistoryService {

	private final ModelMapper mapper;
	private final MonitoringAlertHistoryRepository repository;

	@Override
	public MonitoringAlertHistoryResponse getHistory(Integer pageNumber, Integer pageSize) {
        Page<MonitoringAlertHistory> paged=repository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Constants.ID).descending()));
        
        List<MonitoringAlertHistoryDTO> content=mapper.map(paged.getContent(), new TypeToken<List<MonitoringAlertHistoryDTO>>() {}.getType());
        return new MonitoringAlertHistoryResponse(paged.getNumber(),paged.getSize(),paged.getTotalPages(),paged.getTotalElements(),content);
	}
}
