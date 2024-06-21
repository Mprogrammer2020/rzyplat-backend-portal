package com.rzyplat.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import com.rzyplat.dto.DailyWeatherDTO;
import com.rzyplat.entity.DailyWeather;
import com.rzyplat.impl.DailyWeatherServiceImpl;
import com.rzyplat.repository.DailyWeatherRepository;

@SpringBootTest
public class DailyWeatherServiceTest {

	@Mock
	private ModelMapper modelMapper;
	@Mock
	private DailyWeatherRepository repository;
	@InjectMocks
	private DailyWeatherServiceImpl service;
	
	@Test
	public void testGet10DaysForecast() {
		List<DailyWeatherDTO> expected=List.of(new DailyWeatherDTO(),new DailyWeatherDTO(),new DailyWeatherDTO());
		
		when(repository.findByPropertyNameAndWeatherDateBetween(anyString(),any(LocalDate.class),any(LocalDate.class),any(Sort.class))).thenReturn(new ArrayList<DailyWeather>());
		when(modelMapper.map(any(), any())).thenReturn(expected);
		
		List<DailyWeatherDTO> weather=service.get10DaysForecast("GA",LocalDate.now());
		assertEquals(3, weather.size());
	}
}
