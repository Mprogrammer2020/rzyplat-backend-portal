package com.rzyplat.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;
import com.rzyplat.entity.DeviceType;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.response.DeviceTypeResponse;

public interface DeviceTypeService {

	String createDeviceType(String categoryId,String type, MultipartFile image) throws EntityNotFoundException, IOException;

	DeviceTypeResponse getDevices(Integer page, Integer size, String categoryId);

	Optional<DeviceType> findById(String deviceId) throws EntityNotFoundException;
	
	List<DeviceType> getByCategoryId(String categoryId);

	void save(DeviceType deviceType);
	
	void saveAll(List<DeviceType> deviceTypes);

	Optional<DeviceType> findByTypeAndCategoryId(String type,String categoryId);

}
