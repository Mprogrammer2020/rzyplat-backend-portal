package com.rzyplat.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzyplat.dto.DailyWeatherDTO;
import com.rzyplat.entity.DailyWeather;
import com.rzyplat.impl.DailyWeatherServiceImpl;
import com.rzyplat.repository.DailyWeatherRepository;

@SpringBootTest
public class DailyWeatherServiceTest {

	@Mock
	private ObjectMapper objectMapper;
	@Mock
	private DailyWeatherRepository repository;
	@InjectMocks
	private DailyWeatherServiceImpl service;
	
	@Test
	public void testGet10DaysForecast() {
		List<DailyWeather> expected=List.of(new DailyWeather(),new DailyWeather(),new DailyWeather());
		
		when(repository.find10DaysForecast(anyString(),any(LocalDate.class),any(LocalDate.class),any(Sort.class))).thenReturn(expected);
		List<DailyWeatherDTO> weather=service.get10DaysForecast("GA");
		assertEquals(3, weather.size());
	}
}
