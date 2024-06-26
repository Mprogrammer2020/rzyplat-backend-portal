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
import com.rzyplat.entity.MonitoringAlertSummary;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.impl.MonitoringAlertSummaryServiceImpl;
import com.rzyplat.repository.MonitoringAlertSummaryRepository;
import com.rzyplat.response.MonitoringAlertSummaryResponse;

@SpringBootTest
public class MonitoringAlertSummaryServiceTest {

	@Mock
	private ModelMapper mapper;
	@Mock
	private MonitoringAlertSummaryRepository repository;
	@InjectMocks
	private MonitoringAlertSummaryServiceImpl service;

	@Test
	public void testGetSummaryFound() throws EntityNotFoundException {
		MonitoringAlertSummaryResponse summary=new MonitoringAlertSummaryResponse();
		summary.setActiveAlerts(15);
		summary.setTotalAlerts(20);
		
		when(repository.findAll()).thenReturn(List.of(new MonitoringAlertSummary()));
		when(mapper.map(any(), eq(MonitoringAlertSummaryResponse.class))).thenReturn(summary);

		MonitoringAlertSummaryResponse actual=service.getSummary();
		
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
