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
import com.rzyplat.entity.FireAlertSummary;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.impl.FireAlertSummaryServiceImpl;
import com.rzyplat.repository.FireAlertSummaryRepository;
import com.rzyplat.response.FireAlertSummaryResponse;

@SpringBootTest
public class FireAlertSummaryServiceTest {

	@Mock
	private ModelMapper mapper;
	@Mock
	private FireAlertSummaryRepository repository;
	@InjectMocks
	private FireAlertSummaryServiceImpl service;

	@Test
	public void testGetSummaryFound() throws EntityNotFoundException {
		FireAlertSummaryResponse summary=new FireAlertSummaryResponse();
		summary.setActiveAlerts(15);
		summary.setTotalAlerts(20);
		summary.setTotalDevices(35);
		
		when(repository.findAll()).thenReturn(List.of(new FireAlertSummary()));
		when(mapper.map(any(), eq(FireAlertSummaryResponse.class))).thenReturn(summary);

		FireAlertSummaryResponse actual=service.getSummary();
		
		assertNotNull(actual);
		assertEquals(actual.getActiveAlerts(), 15);
		assertEquals(actual.getTotalAlerts(), 20);
		assertEquals(actual.getTotalDevices(), 35);
	}
	
	@Test
	public void testGetSummaryNotFound() throws EntityNotFoundException{
		when(repository.findAll()).thenReturn(List.of());
		
		assertThrows(EntityNotFoundException.class, () -> service.getSummary());
	}
}
