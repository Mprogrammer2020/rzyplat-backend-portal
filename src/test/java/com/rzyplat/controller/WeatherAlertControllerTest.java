package com.rzyplat.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.rzyplat.dto.WeatherAlertDTO;
import com.rzyplat.impl.WeatherAlertServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class WeatherAlertControllerTest {

	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private WeatherAlertServiceImpl service;

	@Test
	public void testWeatherAlert() throws Exception {
		WeatherAlertDTO dto = new WeatherAlertDTO();
		dto.setTitle("Thunderstorm warning");
		
		when(service.getCurrentWeatherAlert(anyString(),any(LocalDateTime.class))).thenReturn(dto);

		mockMvc.perform(get("/weather/alerts").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value("Thunderstorm warning"));
	}
}
