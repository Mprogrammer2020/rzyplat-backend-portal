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
import com.rzyplat.response.MoldAlertSummaryResponse;
import com.rzyplat.service.MoldAlertSummaryService;

@SpringBootTest
@AutoConfigureMockMvc
public class MoldAlertSummaryControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private MoldAlertSummaryService service;
	
	@Test
	public void getSummaryTest() throws Exception {
		MoldAlertSummaryResponse response=new MoldAlertSummaryResponse();
		response.setTotalAlerts(10);
		response.setActiveAlerts(15);
		
		when(service.getSummary()).thenReturn(response);
		
		mockMvc.perform(get("/alerts/mold"))
		.andExpect(status().isOk())
        .andExpect(jsonPath("$.totalAlerts").value(10))
        .andExpect(jsonPath("$.activeAlerts").value(15));
	}
	
	@Test
	public void getSummaryTestNotFound() throws Exception {
		when(service.getSummary()).thenThrow(EntityNotFoundException.class);
		
		mockMvc.perform(get("/alerts/mold")).andExpect(status().isBadRequest());
	}
}
