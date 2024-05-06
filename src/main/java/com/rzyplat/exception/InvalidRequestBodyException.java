package com.rzyplat.exception;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.validation.BindingResult;

public class InvalidRequestBodyException extends Exception {

	
	private List<String> errors; 

	public InvalidRequestBodyException(BindingResult br) {
		super(String.format("validation failed!"));
		
		this.errors=br.getFieldErrors().stream()
			.map(error -> String.format("field ['%s'] rejected value ['%s'] message ['%s']", error.getField(), error.getRejectedValue(), error.getDefaultMessage()))
			.collect(Collectors.toList());
	}
	
	public List<String> getErrors() {
		return errors;
	}
}
