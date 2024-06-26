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
import com.rzyplat.constant.Constants;
import com.rzyplat.dto.InsuranceAlertHistoryDTO;
import com.rzyplat.entity.InsuranceAlertHistory;
import com.rzyplat.impl.InsuranceAlertHistoryServiceImpl;
import com.rzyplat.repository.InsuranceAlertHistoryRepository;
import com.rzyplat.response.InsuranceAlertHistoryResponse;

@SpringBootTest
public class InsuranceAlertHistoryServiceTest {

	@Mock
	private ModelMapper mapper;
	@Mock
	private InsuranceAlertHistoryRepository repository;
	@InjectMocks
	private InsuranceAlertHistoryServiceImpl service;
	
	@Test
	public void testGetHistory() {
		List<InsuranceAlertHistoryDTO> expected=List.of(new InsuranceAlertHistoryDTO(),new InsuranceAlertHistoryDTO(),new InsuranceAlertHistoryDTO());
		
		Page<InsuranceAlertHistory> paged=new PageImpl<InsuranceAlertHistory>(List.of(new InsuranceAlertHistory()), PageRequest.of(0, 8), 24l);
		
		when(repository.findAll(any(Pageable.class))).thenReturn(paged);
		when(mapper.map(anyList(), eq(new TypeToken<List<InsuranceAlertHistoryDTO>>() {}.getType()))).thenReturn(expected);
		
		InsuranceAlertHistoryResponse actual=service.getHistory(0,8);
		
		assertNotNull(actual);
		assertEquals(actual.getTotalElements(), 24);
		assertEquals(actual.getList().size(), 3);
	}
	
	@Test
	public void testGetHistoryByAlertStatus() {
		List<InsuranceAlertHistoryDTO> expected=List.of(new InsuranceAlertHistoryDTO(),new InsuranceAlertHistoryDTO(),new InsuranceAlertHistoryDTO());
		
		Page<InsuranceAlertHistory> paged=new PageImpl<InsuranceAlertHistory>(List.of(new InsuranceAlertHistory()), PageRequest.of(0, 8), 24l);
		
		when(repository.findByAlertStatus(eq(Constants.SEVERE_ALERT),any(Pageable.class))).thenReturn(paged);
		when(mapper.map(anyList(), eq(new TypeToken<List<InsuranceAlertHistoryDTO>>() {}.getType()))).thenReturn(expected);
		
		InsuranceAlertHistoryResponse actual=service.getHistory(Constants.SEVERE_ALERT,0,8);
		
		assertNotNull(actual);
		assertEquals(actual.getTotalElements(), 24);
		assertEquals(actual.getList().size(), 3);
	}
	
}
