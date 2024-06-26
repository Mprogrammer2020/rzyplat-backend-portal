package com.rzyplat.impl;

import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import com.google.gson.reflect.TypeToken;
import com.rzyplat.dto.MonitoringDeviceDTO;
import com.rzyplat.entity.MonitoringDevice;
import com.rzyplat.repository.MonitoringDevicesRepository;
import com.rzyplat.response.MonitoringDeviceResponse;
import com.rzyplat.service.MonitoringDeviceService;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.query.Query;

@Service
@AllArgsConstructor
public class MonitoringDeviceServiceImpl implements MonitoringDeviceService {
	
	private final ModelMapper mapper;
	private final MongoTemplate mongoTemplate;
	private final MonitoringDevicesRepository repository;

	@Override
	public MonitoringDeviceResponse filter(Integer pageNumber, Integer pageSize, Boolean online,Boolean lowBattery) {
		Criteria criteria = new Criteria();
        if (Objects.nonNull(lowBattery)) {
            criteria.and("batteryLevel").lt(20); 
        }
        
	    if(Objects.nonNull(online)) {
            criteria.and("online").is(online);
        }

        Query query = new Query(criteria);
        long totalElements=mongoTemplate.count(query, MonitoringDevice.class);
        
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        query.with(pageable);

        List<MonitoringDeviceDTO> list = mapper.map(mongoTemplate.find(query, MonitoringDevice.class),new TypeToken<List<MonitoringDeviceDTO>>() {}.getType());
        Page<MonitoringDeviceDTO> paged = new PageImpl<>(list, pageable, totalElements);

        Integer onlineCount=repository.countByOnlineIsTrue();
        Integer offlineCount=repository.countByOnlineIsFalse();
        Integer lowBatteryCount=repository.countByBatteryLevelLessThan(20);
        
        return new MonitoringDeviceResponse(pageNumber, pageSize, paged.getTotalPages(), paged.getTotalElements(),
        		onlineCount, offlineCount, lowBatteryCount, list);
	}
}
