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
import com.rzyplat.dto.MoldAlertHistoryDTO;
import com.rzyplat.entity.MoldAlertHistory;
import com.rzyplat.impl.MoldAlertHistoryServiceImpl;
import com.rzyplat.repository.MoldAlertHistoryRepository;
import com.rzyplat.response.MoldAlertHistoryResponse;

@SpringBootTest
public class MoldAlertHistoryServiceTest {

	@Mock
	private ModelMapper mapper;
	@Mock
	private MoldAlertHistoryRepository repository;
	@InjectMocks
	private MoldAlertHistoryServiceImpl service;
	
	@Test
	public void testGetHistory() {
		List<MoldAlertHistoryDTO> expected=List.of(new MoldAlertHistoryDTO(),new MoldAlertHistoryDTO(),new MoldAlertHistoryDTO());
		
		Page<MoldAlertHistory> paged=new PageImpl<MoldAlertHistory>(List.of(new MoldAlertHistory()), PageRequest.of(0, 8), 24l);
		
		when(repository.findAll(any(Pageable.class))).thenReturn(paged);
		when(mapper.map(anyList(), eq(new TypeToken<List<MoldAlertHistoryDTO>>() {}.getType()))).thenReturn(expected);
		
		MoldAlertHistoryResponse actual=service.getHistory(0,8);
		
		assertNotNull(actual);
		assertEquals(actual.getTotalElements(), 24);
		assertEquals(actual.getList().size(), 3);
	}
	
}
