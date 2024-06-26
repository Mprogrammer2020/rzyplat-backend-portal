package com.rzyplat.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.google.gson.reflect.TypeToken;
import com.rzyplat.dto.MonitoringDeviceDTO;
import com.rzyplat.entity.MonitoringDevice;
import com.rzyplat.impl.MonitoringDeviceServiceImpl;
import com.rzyplat.repository.MonitoringDevicesRepository;
import com.rzyplat.response.MonitoringDeviceResponse;

@SpringBootTest
public class MonitoringDeviceServiceTest {

	@Mock
	private ModelMapper mapper;
	@Mock
	private MongoTemplate mongoTemplate;
	@Mock
	private MonitoringDevicesRepository repository;
	@InjectMocks
	private MonitoringDeviceServiceImpl service;
	
	@Test
	public void testFilter() {
		when(mongoTemplate.count(any(Query.class), eq(MonitoringDevice.class))).thenReturn(100l);
		when(mongoTemplate.find(any(Query.class), eq(MonitoringDevice.class))).thenReturn(List.of(new MonitoringDevice()));
		when(mapper.map(anyList(), eq(new TypeToken<List<MonitoringDeviceDTO>>() {}.getType()))).thenReturn(List.of(new MonitoringDeviceDTO()));
		
		when(repository.countByOnlineIsTrue()).thenReturn(15);
		when(repository.countByOnlineIsFalse()).thenReturn(10);
		when(repository.countByBatteryLevelLessThan(anyInt())).thenReturn(5);
		
		MonitoringDeviceResponse actual=service.filter(0, 10, true, true);
		
		assertNotNull(actual);
		assertEquals(actual.getTotalElements(), 100);
		assertEquals(actual.getOnlineCount(), 15);
		assertEquals(actual.getOfflineCount(), 10);
		assertEquals(actual.getLowbatteryCount(), 5);
	}
}
