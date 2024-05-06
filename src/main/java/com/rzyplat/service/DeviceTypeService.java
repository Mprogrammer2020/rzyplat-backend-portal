package com.rzyplat.service;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.rzyplat.entity.DeviceType;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.response.DeviceTypeResponse;

public interface DeviceTypeService {

	String createDeviceType(String categoryId,String type, MultipartFile image) throws EntityNotFoundException, IOException;

	DeviceTypeResponse getDevices(Integer pageNumber, Integer pageSize, String categoryId);

	DeviceType findById(String deviceId) throws EntityNotFoundException;
	
	List<DeviceType> getByCategoryId(String categoryId);

	String save(DeviceType deviceType);
	
	String saveAll(List<DeviceType> deviceTypes);

	DeviceType findByTypeAndCategoryId(String type,String categoryId) throws EntityNotFoundException;

}
