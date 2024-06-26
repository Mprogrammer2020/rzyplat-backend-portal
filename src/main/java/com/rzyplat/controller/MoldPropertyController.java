package com.rzyplat.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rzyplat.response.MoldPropertyResponse;
import com.rzyplat.service.MoldPropertyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/mold/properties")
public class MoldPropertyController {

	private final MoldPropertyService moldPropertyService;
	
	@GetMapping
	public ResponseEntity<List<MoldPropertyResponse>> getProperties() {
		log.info("getProperties started at {}", System.currentTimeMillis());
		List<MoldPropertyResponse> properties=moldPropertyService.getProperties();
		log.info("getProperties finished at {}", System.currentTimeMillis());
		return new ResponseEntity<>(properties, HttpStatus.OK);
	}
}
