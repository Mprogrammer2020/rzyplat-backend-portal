package com.rzyplat.service;

import org.springframework.web.multipart.MultipartFile;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.request.CreateDeviceRequest;
import com.rzyplat.response.DeviceResponse;

public interface DeviceService {

	String createDevice(CreateDeviceRequest createDeviceRequest) throws EntityNotFoundException;

	DeviceResponse searchDevice(Integer pageNumber, Integer pageSize, String categoryId, String deviceTypeId) throws EntityNotFoundException;

	String deleteDeviceById(String deviceId) throws Exception;

	String createBulkDevices(MultipartFile file) throws Exception;

}
