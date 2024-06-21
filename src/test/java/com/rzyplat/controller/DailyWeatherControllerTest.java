package com.rzyplat.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.rzyplat.dto.DailyWeatherDTO;
import com.rzyplat.impl.DailyWeatherServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class DailyWeatherControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private DailyWeatherServiceImpl service;

	@Test
	public void testGet10DaysForecast() throws Exception {
		List<DailyWeatherDTO> expected=List.of(new DailyWeatherDTO(),new DailyWeatherDTO(),new DailyWeatherDTO());
		
		when(service.get10DaysForecast(anyString(),any(LocalDate.class))).thenReturn(expected);

		mockMvc.perform(get("/weather/daily").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray());
	}
}
