package com.rzyplat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> entiyNotFound(EntityNotFoundException e) {
		e.printStackTrace();
		
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidRequestBodyException.class)
	public ResponseEntity<?> invalidRequestBody(InvalidRequestBodyException e) {
		e.printStackTrace();
		
		return new ResponseEntity<>(e.getErrors(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidDataFormatException.class)
	public ResponseEntity<?> invalidFormatException(InvalidDataFormatException e) {
		e.printStackTrace();
		
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(EmptyFileException.class)
	public ResponseEntity<?> EmptyFileException(EmptyFileException e) {
		e.printStackTrace();
		
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException e) {
		e.printStackTrace();
		
		return new ResponseEntity<>(e.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> exception(Exception e) {
		e.printStackTrace();
		
		return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
