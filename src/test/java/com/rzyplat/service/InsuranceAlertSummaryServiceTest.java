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
import com.rzyplat.entity.InsuranceAlertSummary;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.impl.InsuranceAlertSummaryServiceImpl;
import com.rzyplat.repository.InsuranceAlertSummaryRepository;
import com.rzyplat.response.InsuranceAlertSummaryResponse;

@SpringBootTest
public class InsuranceAlertSummaryServiceTest {

	@Mock
	private ModelMapper mapper;
	@Mock
	private InsuranceAlertSummaryRepository repository;
	@InjectMocks
	private InsuranceAlertSummaryServiceImpl service;

	@Test
	public void testGetSummaryFound() throws EntityNotFoundException {
		InsuranceAlertSummaryResponse summary=new InsuranceAlertSummaryResponse();
		summary.setActiveAlerts(15);
		summary.setTotalAlerts(20);
		summary.setTotalClaims(3);
		summary.setSevereAlerts(5);
		
		when(repository.findAll()).thenReturn(List.of(new InsuranceAlertSummary()));
		when(mapper.map(any(), eq(InsuranceAlertSummaryResponse.class))).thenReturn(summary);

		InsuranceAlertSummaryResponse actual=service.getSummary();
		
		assertNotNull(actual);
		assertEquals(actual.getActiveAlerts(), 15);
		assertEquals(actual.getTotalAlerts(), 20);
		assertEquals(actual.getTotalClaims(), 3);
		assertEquals(actual.getSevereAlerts(), 5);
	}
	
	@Test
	public void testGetSummaryNotFound() throws EntityNotFoundException{
		when(repository.findAll()).thenReturn(List.of());
		
		assertThrows(EntityNotFoundException.class, () -> service.getSummary());
	}
}
