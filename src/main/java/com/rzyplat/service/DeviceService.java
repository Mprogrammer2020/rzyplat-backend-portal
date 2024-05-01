package com.rzyplat.service;

import javax.validation.Valid;
import org.springframework.web.multipart.MultipartFile;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.request.CreateDeviceRequest;
import com.rzyplat.request.DeviceSearchParam;
import com.rzyplat.response.DeviceResponse;

public interface DeviceService {

	String createDevice(@Valid CreateDeviceRequest createDeviceRequest) throws EntityNotFoundException;

	DeviceResponse getDevices(DeviceSearchParam search);

	String deleteDeviceById(String deviceId) throws Exception;

	String createBulkDevices(MultipartFile file) throws Exception;

}
