package com.rzyplat.controller;

import java.util.List;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.rzyplat.dto.HourlyWeatherDTO;
import com.rzyplat.service.HourlyWeatherService;

@SpringBootTest
@AutoConfigureMockMvc
public class HourlyWeatherControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private HourlyWeatherService service;
    
    public void get24HoursForecast() throws Exception {
    	
    	List<HourlyWeatherDTO> expected=List.of(new HourlyWeatherDTO(),new HourlyWeatherDTO(),new HourlyWeatherDTO());
		
		when(service.get24HoursForecast(anyString())).thenReturn(expected);

		mockMvc.perform(get("/weather/hourly").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray());
    }
    
    public void getCurrentWeather() throws Exception {
    	HourlyWeatherDTO expected=new HourlyWeatherDTO();
    	expected.setAirQuality(100);
		
		when(service.getCurrentWeather(anyString())).thenReturn(expected);

		mockMvc.perform(get("/weather/hourly/current").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.airQuality").value(100));
    }

	public void getCurrentWeatherForProperties() throws Exception {
		List<HourlyWeatherDTO> expected=List.of(new HourlyWeatherDTO(),new HourlyWeatherDTO(),new HourlyWeatherDTO());
		
		when(service.getCurrentWeatherForProperties()).thenReturn(expected);

		mockMvc.perform(get("/weather/hourly/current/properties").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray());
	}
    
    
}
