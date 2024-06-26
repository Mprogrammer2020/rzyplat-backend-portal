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
import com.rzyplat.dto.FireAlertHistoryDTO;
import com.rzyplat.entity.FireAlertHistory;
import com.rzyplat.impl.FireAlertHistoryServiceImpl;
import com.rzyplat.repository.FireAlertHistoryRepository;
import com.rzyplat.response.FireAlertHistoryResponse;

@SpringBootTest
public class FireAlertHistoryServiceTest {

	@Mock
	private ModelMapper mapper;
	@Mock
	private FireAlertHistoryRepository repository;
	@InjectMocks
	private FireAlertHistoryServiceImpl service;
	
	@Test
	public void testGetHistory() {
		List<FireAlertHistoryDTO> expected=List.of(new FireAlertHistoryDTO(),new FireAlertHistoryDTO(),new FireAlertHistoryDTO());
		
		Page<FireAlertHistory> paged=new PageImpl<FireAlertHistory>(List.of(new FireAlertHistory()), PageRequest.of(0, 8), 24l);
		
		when(repository.findAll(any(Pageable.class))).thenReturn(paged);
		when(mapper.map(anyList(), eq(new TypeToken<List<FireAlertHistoryDTO>>() {}.getType()))).thenReturn(expected);
		
		FireAlertHistoryResponse actual=service.getHistory(0,8);
		
		assertNotNull(actual);
		assertEquals(actual.getTotalElements(), 24);
		assertEquals(actual.getList().size(), 3);
	}
	
}
