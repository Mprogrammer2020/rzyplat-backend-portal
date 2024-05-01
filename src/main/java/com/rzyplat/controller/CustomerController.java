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
import com.rzyplat.exception.InvalidRequestBodyException;
import com.rzyplat.request.CreateCustomerRequest;
import com.rzyplat.request.SearchParam;
import com.rzyplat.response.CustomerSearchResponse;
import com.rzyplat.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
	
	private final CustomerService service;
	
	@PostMapping
	public ResponseEntity<String> createCustomer(@Valid @RequestBody CreateCustomerRequest customerCreateRequest,BindingResult br) throws Exception {
		log.info("createCustomer started at {}", System.currentTimeMillis());
		if(br.hasErrors()) {
			throw new InvalidRequestBodyException(br);
		}
		String message= service.createCustomer(customerCreateRequest);
		log.info("createCustomer finished at {}", System.currentTimeMillis());
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}
	
	@GetMapping("/search")
	public ResponseEntity<CustomerSearchResponse> searchCustomer(
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
			@RequestParam(required = false) String orderBy, @RequestParam(required = false) String direction) {
		log.info("searchCustomer started at {}", System.currentTimeMillis());
		SearchParam search=new SearchParam(page, size, orderBy, direction);
		CustomerSearchResponse customerSearchResponse=service.searchCustomers(search);
		log.info("searchCustomer finished at {}", System.currentTimeMillis());
		return new ResponseEntity<>(customerSearchResponse, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable String id) throws Exception {
		log.info("deleteCustomer started at {}", System.currentTimeMillis());
		String message = service.deleteCustomerById(id);
		log.info("deleteCustomer finished at {}", System.currentTimeMillis());
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
}
