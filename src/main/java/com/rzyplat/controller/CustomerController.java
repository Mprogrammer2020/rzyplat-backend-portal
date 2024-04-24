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
import com.rzyplat.entity.Customer;
import com.rzyplat.exception.InvalidRequestBodyException;
import com.rzyplat.request.CreateCustomerRequest;
import com.rzyplat.request.SearchCustomerParam;
import com.rzyplat.response.SearchResponse;
import com.rzyplat.response.GenericResponse;
import com.rzyplat.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	private final CustomerService service;
	public CustomerController(CustomerService service) {
		this.service=service;
	} 
	
	@PostMapping
	public ResponseEntity<GenericResponse<Customer>> createCustomer(@Valid @RequestBody CreateCustomerRequest customerCreateRequest,BindingResult br) throws Exception {
		if(br.hasErrors()) {
			throw new InvalidRequestBodyException(br);
		}
		GenericResponse<Customer> creationResponse = service.createCustomer(customerCreateRequest);
		return new ResponseEntity<>(creationResponse, HttpStatus.CREATED);
	}
	
	@GetMapping("/search")
	public ResponseEntity<SearchResponse<Customer>> searchCustomer(
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
			@RequestParam(required = false) String orderBy, @RequestParam(required = false) String direction) {
		
		SearchCustomerParam search=new SearchCustomerParam(page, size, orderBy, direction);
		SearchResponse<Customer> customerSearchResponse=service.searchCustomers(search);
		return new ResponseEntity<>(customerSearchResponse, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<GenericResponse<Customer>> delete(@PathVariable String id) throws Exception {
		GenericResponse<Customer> deletionResponse = service.deleteCustomerById(id);
		return new ResponseEntity<>(deletionResponse, HttpStatus.OK);
	}
}
