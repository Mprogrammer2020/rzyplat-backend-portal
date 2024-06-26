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
import com.rzyplat.dto.SecurityAlertHistoryDTO;
import com.rzyplat.entity.SecurityAlertHistory;
import com.rzyplat.impl.SecurityAlertHistoryServiceImpl;
import com.rzyplat.repository.SecurityAlertHistoryRepository;
import com.rzyplat.response.SecurityAlertHistoryResponse;

@SpringBootTest
public class SecurityAlertHistoryServiceTest {

	@Mock
	private ModelMapper mapper;
	@Mock
	private SecurityAlertHistoryRepository repository;
	@InjectMocks
	private SecurityAlertHistoryServiceImpl service;
	
	@Test
	public void testGetHistory() {
		List<SecurityAlertHistoryDTO> expected=List.of(new SecurityAlertHistoryDTO(),new SecurityAlertHistoryDTO(),new SecurityAlertHistoryDTO());
		
		Page<SecurityAlertHistory> paged=new PageImpl<SecurityAlertHistory>(List.of(new SecurityAlertHistory()), PageRequest.of(0, 8), 24l);
		
		when(repository.findAll(any(Pageable.class))).thenReturn(paged);
		when(mapper.map(anyList(), eq(new TypeToken<List<SecurityAlertHistoryDTO>>() {}.getType()))).thenReturn(expected);
		
		SecurityAlertHistoryResponse actual=service.getHistory(0,8);
		
		assertNotNull(actual);
		assertEquals(actual.getTotalElements(), 24);
		assertEquals(actual.getList().size(), 3);
	}
	
}
