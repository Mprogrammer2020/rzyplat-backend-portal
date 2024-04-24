package com.rzyplat.exception;

public class EntityNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(String entityName,String id) {
		super(String.format("No %s with id %s was found!", entityName, id));
	}
	
	
}
