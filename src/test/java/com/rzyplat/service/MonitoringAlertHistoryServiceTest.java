package com.rzyplat.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.google.gson.reflect.TypeToken;
import com.rzyplat.dto.MonitoringAlertHistoryDTO;
import com.rzyplat.entity.MonitoringAlertHistory;
import com.rzyplat.impl.MonitoringAlertHistoryServiceImpl;
import com.rzyplat.repository.MonitoringAlertHistoryRepository;
import com.rzyplat.response.MonitoringAlertHistoryResponse;

@SpringBootTest
public class MonitoringAlertHistoryServiceTest {

	@Mock
	private ModelMapper mapper;
	@Mock
	private MonitoringAlertHistoryRepository repository;
	@InjectMocks
	private MonitoringAlertHistoryServiceImpl service;
	
	@Test
	public void testGetHistory() {
		List<MonitoringAlertHistoryDTO> expected=List.of(new MonitoringAlertHistoryDTO(),new MonitoringAlertHistoryDTO(),new MonitoringAlertHistoryDTO());
		
		Page<MonitoringAlertHistory> paged=new PageImpl<MonitoringAlertHistory>(List.of(new MonitoringAlertHistory()), PageRequest.of(0, 8), 24l);
		
		when(repository.findAll(any(Pageable.class))).thenReturn(paged);
		when(mapper.map(anyList(), eq(new TypeToken<List<MonitoringAlertHistoryDTO>>() {}.getType()))).thenReturn(expected);
		
		MonitoringAlertHistoryResponse actual=service.getHistory(0,8);
		
		assertNotNull(actual);
		assertEquals(actual.getTotalElements(), 24);
		assertEquals(actual.getList().size(), 3);
	}
	
}
