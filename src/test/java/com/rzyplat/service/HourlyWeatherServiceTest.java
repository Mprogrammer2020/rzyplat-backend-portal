package com.rzyplat.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import com.rzyplat.dto.HourlyWeatherDTO;
import com.rzyplat.entity.HourlyWeather;
import com.rzyplat.impl.HourlyWeatherServiceImpl;
import com.rzyplat.repository.HourlyWeatherRepository;

@SpringBootTest
public class HourlyWeatherServiceTest {

	@Mock
	private ModelMapper modelMapper;
	@Mock
	private HourlyWeatherRepository repository;
	@InjectMocks
	private HourlyWeatherServiceImpl service;
	
	@Test
	public void testGetCurrentWeather() throws Exception {
		HourlyWeatherDTO expected=new HourlyWeatherDTO();
		expected.setAirQuality(90);
		
		when(repository.findByPropertyNameAndWeatherTime(anyString(),any(LocalDateTime.class))).thenReturn(Optional.of(new HourlyWeather()));
		when(modelMapper.map(any(), eq(HourlyWeatherDTO.class))).thenReturn(expected);
		
		HourlyWeatherDTO actual=service.getCurrentWeather("GA",LocalDateTime.now());
		
		assertNotNull(actual);
		assertEquals(actual.getAirQuality(), 90);
	}

	@Test
	public void testGetCurrentWeatherForProperties() throws Exception {
		List<HourlyWeatherDTO> expected=List.of(new HourlyWeatherDTO(),new HourlyWeatherDTO(),new HourlyWeatherDTO());
		
		when(repository.findByPropertyNameInAndWeatherTime(anyList(),any(LocalDateTime.class))).thenReturn(new ArrayList<HourlyWeather>());
		when(modelMapper.map(any(), any())).thenReturn(expected);
		
		List<HourlyWeatherDTO> actual=service.getCurrentWeatherForProperties(LocalDateTime.now());
		
		assertNotNull(actual);
		assertEquals(actual.size(), 3);
	}
	
	@Test
	public void testGet24HoursForecast() {
		List<HourlyWeatherDTO> expected=List.of(new HourlyWeatherDTO(),new HourlyWeatherDTO(),new HourlyWeatherDTO());
		
		when(repository.findByPropertyNameAndWeatherTimeBetween(anyString(),any(LocalDateTime.class),any(LocalDateTime.class),any(Sort.class))).thenReturn(new ArrayList<HourlyWeather>());
		when(modelMapper.map(any(), any())).thenReturn(expected);
		
		List<HourlyWeatherDTO> actual=service.get24HoursForecast("GA",LocalDateTime.now());
		
		assertNotNull(actual);
		assertEquals(actual.size(), 3);
	}
}
