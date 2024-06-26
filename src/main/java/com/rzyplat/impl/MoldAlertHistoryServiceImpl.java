package com.rzyplat.impl;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.google.gson.reflect.TypeToken;
import com.rzyplat.constant.Constants;
import com.rzyplat.dto.MoldAlertHistoryDTO;
import com.rzyplat.entity.MoldAlertHistory;
import com.rzyplat.repository.MoldAlertHistoryRepository;
import com.rzyplat.response.MoldAlertHistoryResponse;
import com.rzyplat.service.MoldAlertHistoryService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MoldAlertHistoryServiceImpl implements MoldAlertHistoryService {
	
	private final ModelMapper mapper;
	private final MoldAlertHistoryRepository repository;

	@Override
	public MoldAlertHistoryResponse getHistory(Integer pageNumber, Integer pageSize) {
        Page<MoldAlertHistory> paged=repository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Constants.ID).descending()));
        
        List<MoldAlertHistoryDTO> content=mapper.map(paged.getContent(), new TypeToken<List<MoldAlertHistoryDTO>>() {}.getType());
        return new MoldAlertHistoryResponse(paged.getNumber(),paged.getSize(),paged.getTotalPages(),paged.getTotalElements(),content);
	}
}
