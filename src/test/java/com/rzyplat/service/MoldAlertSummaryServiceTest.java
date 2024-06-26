package com.rzyplat.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import com.rzyplat.entity.MoldAlertSummary;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.impl.MoldAlertSummaryServiceImpl;
import com.rzyplat.repository.MoldAlertSummaryRepository;
import com.rzyplat.response.MoldAlertSummaryResponse;

@SpringBootTest
public class MoldAlertSummaryServiceTest {

	@Mock
	private ModelMapper mapper;
	@Mock
	private MoldAlertSummaryRepository repository;
	@InjectMocks
	private MoldAlertSummaryServiceImpl service;

	@Test
	public void testGetSummaryFound() throws EntityNotFoundException {
		MoldAlertSummaryResponse summary=new MoldAlertSummaryResponse();
		summary.setActiveAlerts(15);
		summary.setTotalAlerts(20);
		
		when(repository.findAll()).thenReturn(List.of(new MoldAlertSummary()));
		when(mapper.map(any(), eq(MoldAlertSummaryResponse.class))).thenReturn(summary);

		MoldAlertSummaryResponse actual=service.getSummary();
		
		assertNotNull(actual);
		assertEquals(actual.getActiveAlerts(), 15);
		assertEquals(actual.getTotalAlerts(), 20);
	}
	
	@Test
	public void testGetSummaryNotFound() throws EntityNotFoundException{
		when(repository.findAll()).thenReturn(List.of());
		
		assertThrows(EntityNotFoundException.class, () -> service.getSummary());
	}
}
