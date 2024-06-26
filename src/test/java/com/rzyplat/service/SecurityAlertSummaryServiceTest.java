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
import com.rzyplat.entity.SecurityAlertSummary;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.impl.SecurityAlertSummaryServiceImpl;
import com.rzyplat.repository.SecurityAlertSummaryRepository;
import com.rzyplat.response.SecurityAlertSummaryResponse;

@SpringBootTest
public class SecurityAlertSummaryServiceTest {

	@Mock
	private ModelMapper mapper;
	@Mock
	private SecurityAlertSummaryRepository repository;
	@InjectMocks
	private SecurityAlertSummaryServiceImpl service;

	@Test
	public void testGetSummaryFound() throws EntityNotFoundException {
		SecurityAlertSummaryResponse summary=new SecurityAlertSummaryResponse();
		summary.setActiveAlerts(15);
		summary.setTotalAlerts(20);
		
		when(repository.findAll()).thenReturn(List.of(new SecurityAlertSummary()));
		when(mapper.map(any(), eq(SecurityAlertSummaryResponse.class))).thenReturn(summary);

		SecurityAlertSummaryResponse actual=service.getSummary();
		
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
