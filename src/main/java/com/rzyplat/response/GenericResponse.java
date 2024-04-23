package com.rzyplat.response;

import com.rzyplat.constant.Action;

public class GenericResponse<T> {

	private Action action;
	private String message;
	private T data;
	
	public GenericResponse(Action action, String message, T data) {
		super();
		this.action = action;
		this.message = message;
		this.data = data;
	}
	
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}
