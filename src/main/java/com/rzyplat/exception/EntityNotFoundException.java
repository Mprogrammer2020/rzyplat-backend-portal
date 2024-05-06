package com.rzyplat.exception;

public class EntityNotFoundException extends Exception {

	
	public EntityNotFoundException(String message) {
		super(message);
	}
	
	public EntityNotFoundException(String entityName,String key,String value) {
		super(String.format("No %s with %s '%s' was found!", entityName,key,value));
	}
	
	
}
