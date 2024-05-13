package com.rzyplat.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rzyplat.dto.DeviceTypeDTO;
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
	
	@GetMapping("/basic/{categoryId}")
	public ResponseEntity<List<DeviceTypeDTO>> getDeviceType(@PathVariable String categoryId){
		log.info("getDeviceType-basic started at {}", System.currentTimeMillis());
		List<DeviceTypeDTO> deviceTypes=service.getDeviceTypes(categoryId);
		log.info("getDeviceType-basic finished at {}", System.currentTimeMillis());
		return new ResponseEntity<>(deviceTypes, HttpStatus.OK);

	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<DeviceTypeResponse> getDeviceType(@PathVariable String categoryId,
			                                    @RequestParam(defaultValue = "0", required = false) Integer pageNumber,
			                                    @RequestParam(defaultValue = "10", required = false) Integer pageSize){
		log.info("getDeviceType started at {}", System.currentTimeMillis());
		DeviceTypeResponse deviceTypeResponse=service.getDeviceTypes(pageNumber,pageSize,categoryId);
		log.info("getDevices finished at {}", System.currentTimeMillis());
		return new ResponseEntity<>(deviceTypeResponse, HttpStatus.OK);

	}

	

}
