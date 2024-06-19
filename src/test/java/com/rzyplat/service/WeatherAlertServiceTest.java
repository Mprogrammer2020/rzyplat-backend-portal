package com.rzyplat.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzyplat.dto.WeatherAlertDTO;
import com.rzyplat.entity.WeatherAlert;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.impl.WeatherAlertServiceImpl;
import com.rzyplat.repository.WeatherAlertRepository;

@SpringBootTest
public class WeatherAlertServiceTest {

	@Mock
	private ObjectMapper mapper;
	
	@Mock
	private WeatherAlertRepository repository;
	
	@InjectMocks
	private WeatherAlertServiceImpl service;
	
	@Test
	public void testGetCurrentWeatherAlert() throws Exception {
		WeatherAlertDTO alertDTO=new WeatherAlertDTO();
		alertDTO.setTitle("Thunderstorm Warning");
		
		when(repository.findByPropertyNameAndAlertTime(eq("GA"), any(LocalDateTime.class))).thenReturn(Optional.of(new WeatherAlert()));
		when(mapper.convertValue(any(), eq(WeatherAlertDTO.class))).thenReturn(alertDTO);
		
		WeatherAlertDTO result=service.getCurrentWeatherAlert("GA");
		
		assertNotNull(result);
		assertEquals(result.getTitle(), "Thunderstorm Warning");
	}
	
	@Test
	public void testGetCurrentWeatherNotFound() throws Exception {
		when(repository.findByPropertyNameAndAlertTime(eq("GAP"), any(LocalDateTime.class))).thenReturn(Optional.empty());
		
		assertThrows(EntityNotFoundException.class, () -> service.getCurrentWeatherAlert("GAP"));
	}
	
}
