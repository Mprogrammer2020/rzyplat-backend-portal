package com.rzyplat.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.rzyplat.response.CategoryResponse;
import com.rzyplat.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
   
	private final CategoryService service;
		
	@PostMapping
	public ResponseEntity<String> createCategory(@RequestParam String name,@RequestParam MultipartFile image) throws IOException{
		log.info("createCategory started at {}", System.currentTimeMillis());
		String message= service.createCategory(name,image);
		log.info("createCategory finished at {}", System.currentTimeMillis());
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}	
	
	@GetMapping
	public ResponseEntity<CategoryResponse> searchCategory(
			@RequestParam(defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(defaultValue = "10", required = false) Integer pageSize,
	        @RequestParam(required = false) String orderBy,
	        @RequestParam(required = false) String direction) {
		log.info("categoryResponse started at {}", System.currentTimeMillis());
		CategoryResponse categoryResponse=service.searchCategory(pageNumber, pageSize, orderBy, direction);
		log.info("categoryResponse finished at {}", System.currentTimeMillis());
		return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
	}

}
