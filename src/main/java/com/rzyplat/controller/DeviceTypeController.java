package com.rzyplat.controller;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.response.DeviceTypeResponse;
import com.rzyplat.service.DeviceTypeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/device-type")
public class DeviceTypeController {
	private final DeviceTypeService service;
	
	@PostMapping("/{categoryId}")
	public ResponseEntity<String> createDeviceType(@PathVariable String categoryId,
			                                       @RequestParam String type,@RequestParam MultipartFile image) throws EntityNotFoundException, IOException{
		log.info("createDeviceType started at {}", System.currentTimeMillis());
		String message= service.createDeviceType(categoryId,type,image);
		log.info("createDeviceType finished at {}", System.currentTimeMillis());
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<DeviceTypeResponse> getDeviceType(@PathVariable String categoryId,
			                                    @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size){
		log.info("getDeviceType started at {}", System.currentTimeMillis());
		DeviceTypeResponse deviceTypeResponse=service.getDevices(page,size,categoryId);
		log.info("getDevices finished at {}", System.currentTimeMillis());
		return new ResponseEntity<>(deviceTypeResponse, HttpStatus.OK);

	}

	

}
