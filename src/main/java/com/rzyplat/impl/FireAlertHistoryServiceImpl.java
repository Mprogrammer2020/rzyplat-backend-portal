package com.rzyplat.impl;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.google.gson.reflect.TypeToken;
import com.rzyplat.constant.Constants;
import com.rzyplat.dto.FireAlertHistoryDTO;
import com.rzyplat.entity.FireAlertHistory;
import com.rzyplat.repository.FireAlertHistoryRepository;
import com.rzyplat.response.FireAlertHistoryResponse;
import com.rzyplat.service.FireAlertHistoryService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FireAlertHistoryServiceImpl implements FireAlertHistoryService {
	
	private final ModelMapper mapper;
	private final FireAlertHistoryRepository repository;

	@Override
	public FireAlertHistoryResponse getHistory(Integer pageNumber, Integer pageSize) {
        Page<FireAlertHistory> paged=repository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Constants.ID).descending()));
        
        List<FireAlertHistoryDTO> content=mapper.map(paged.getContent(), new TypeToken<List<FireAlertHistoryDTO>>() {}.getType());
        return new FireAlertHistoryResponse(paged.getNumber(),paged.getSize(),paged.getTotalPages(),paged.getTotalElements(),content);
	}
}
