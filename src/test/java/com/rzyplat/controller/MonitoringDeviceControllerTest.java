package com.rzyplat.controller;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.any;
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
import com.rzyplat.response.MonitoringDeviceResponse;
import com.rzyplat.service.MonitoringDeviceService;

@SpringBootTest
@AutoConfigureMockMvc
public class MonitoringDeviceControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private MonitoringDeviceService service;
	
	@Test
	public void filterTest() throws Exception {
		MonitoringDeviceResponse response=new MonitoringDeviceResponse(0, 10, 3, 30l, 0, 0, 0, List.of());
		
		when(service.filter(eq(0), eq(10), any(), any())).thenReturn(response);
		
		mockMvc.perform(get("/monitoring/devices")
			.queryParam("online", "true")
			.queryParam("lowBattery", "true")
		)
		.andExpect(status().isOk())
        .andExpect(jsonPath("$.totalPages").value(3))
        .andExpect(jsonPath("$.totalElements").value(30))
        .andExpect(jsonPath("$.list").isArray());
	}
}
