package com.rzyplat.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.rzyplat.response.MonitoringAlertHistoryResponse;
import com.rzyplat.service.MonitoringAlertHistoryService;

@SpringBootTest
@AutoConfigureMockMvc
public class MonitoringAlertHistoryControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private MonitoringAlertHistoryService service;
	
	@Test
	public void getHistoryTest() throws Exception {
		MonitoringAlertHistoryResponse response=new MonitoringAlertHistoryResponse(0, 10, 2, 20l, List.of());
		
		when(service.getHistory(0,10)).thenReturn(response);
		
		mockMvc.perform(get("/alerts/monitoring/history"))
		.andExpect(status().isOk())
        .andExpect(jsonPath("$.totalPages").value(2))
        .andExpect(jsonPath("$.totalElements").value(20))
        .andExpect(jsonPath("$.list").isArray());
	}
	
}
