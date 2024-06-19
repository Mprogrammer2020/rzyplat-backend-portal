package com.rzyplat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rzyplat.exception.InvalidRequestBodyException;
import com.rzyplat.request.ContactSearchResponse;
import com.rzyplat.request.CreateContactRequest;
import com.rzyplat.request.UpdateContactRequest;
import com.rzyplat.service.ContactService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/contacts")
public class ContactController {

	private final ContactService service;
	
	@PostMapping
	public ResponseEntity<String> createContact(@Valid @RequestBody CreateContactRequest contactCreateRequest,BindingResult br) throws Exception {
		log.info("createContact started at {}", System.currentTimeMillis());
		if(br.hasErrors()) {
			throw new InvalidRequestBodyException(br);
		}
		String message= service.createContact(contactCreateRequest);
		log.info("createContact finished at {}", System.currentTimeMillis());
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}
	
	@PutMapping("/{contactId}")
	public ResponseEntity<String> updateContact(@PathVariable String contactId, @Valid @RequestBody UpdateContactRequest updateContactRequest,BindingResult br) throws Exception {
		log.info("updateContact started at {}", System.currentTimeMillis());
		if(br.hasErrors()) {
			throw new InvalidRequestBodyException(br);
		}
		String message= service.updateContact(contactId, updateContactRequest);
		log.info("updateContact finished at {}", System.currentTimeMillis());
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<ContactSearchResponse> searchContact(
			@RequestParam(defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(defaultValue = "10", required = false) Integer pageSize) {
		ContactSearchResponse contactSearchResponse=service.searchContact(pageNumber, pageSize);
		log.info("searchContact finished at {}", System.currentTimeMillis());
		return new ResponseEntity<>(contactSearchResponse, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteContact(@PathVariable String id) throws Exception {
		log.info("deleteContact started at {}", System.currentTimeMillis());
		String message = service.deleteContactById(id);
		log.info("deleteContact finished at {}", System.currentTimeMillis());
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
}
