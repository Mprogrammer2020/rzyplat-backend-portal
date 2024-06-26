package com.rzyplat.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.response.InsuranceAlertSummaryResponse;
import com.rzyplat.service.InsuranceAlertSummaryService;

@SpringBootTest
@AutoConfigureMockMvc
public class InsuranceAlertSummaryControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private InsuranceAlertSummaryService service;
	
	@Test
	public void getSummaryTest() throws Exception {
		InsuranceAlertSummaryResponse response=new InsuranceAlertSummaryResponse();
		response.setTotalAlerts(10);
		response.setActiveAlerts(15);
		response.setTotalClaims(5);
		response.setSevereAlerts(3);
		
		when(service.getSummary()).thenReturn(response);
		
		mockMvc.perform(get("/alerts/insurance"))
		.andExpect(status().isOk())
        .andExpect(jsonPath("$.totalAlerts").value(10))
        .andExpect(jsonPath("$.activeAlerts").value(15))
        .andExpect(jsonPath("$.totalClaims").value(5))
        .andExpect(jsonPath("$.severeAlerts").value(3));
	}
	
	@Test
	public void getSummaryTestNotFound() throws Exception {
		when(service.getSummary()).thenThrow(EntityNotFoundException.class);
		
		mockMvc.perform(get("/alerts/insurance")).andExpect(status().isBadRequest());
	}
}
