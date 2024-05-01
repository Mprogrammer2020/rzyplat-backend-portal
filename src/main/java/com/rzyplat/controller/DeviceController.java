package com.rzyplat.controller;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rzyplat.exception.InvalidRequestBodyException;
import com.rzyplat.request.CreateDeviceRequest;
import com.rzyplat.request.DeviceSearchParam;
import com.rzyplat.response.DeviceResponse;
import com.rzyplat.service.DeviceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/devices")
public class DeviceController {
	private final DeviceService service;
	
	
	@PostMapping
	public ResponseEntity<String> createDevice(@Valid @RequestBody CreateDeviceRequest createDeviceRequest,BindingResult br) throws Exception {
		log.info("createDevice started at {}", System.currentTimeMillis());
		if(br.hasErrors()) {
			throw new InvalidRequestBodyException(br);
		}
		String message= service.createDevice(createDeviceRequest);
		log.info("createDevice finished at {}", System.currentTimeMillis());
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}
	
	@PostMapping("/bulk-upload")
	public ResponseEntity<String> createBulkDevices(@RequestParam MultipartFile file) throws Exception {
		log.info("createDevice started at {}", System.currentTimeMillis());
		String message = service.createBulkDevices(file);
		log.info("createDevice finished at {}", System.currentTimeMillis());
		return new ResponseEntity<>(message, HttpStatus.CREATED);    
	}
		 
	@GetMapping
	public ResponseEntity<DeviceResponse> getDevices(
			@RequestParam(required=false) String categoryId, @RequestParam(required=false) String deviceTypeId,
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
		log.info("getDevices started at {}", System.currentTimeMillis());
		DeviceResponse deviceResponse=service.getDevices(new DeviceSearchParam(page, size, categoryId, deviceTypeId));
		log.info("getDevices finished at {}", System.currentTimeMillis());
		return new ResponseEntity<>(deviceResponse, HttpStatus.OK);
	}
	
	@DeleteMapping("/{deviceId}")
	public ResponseEntity<String> deleteDevice(@PathVariable String deviceId) throws Exception {
		log.info("deleteDevice started at {}", System.currentTimeMillis());
		String message = service.deleteDeviceById(deviceId);
		log.info("deleteDevice finished at {}", System.currentTimeMillis());
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
}





