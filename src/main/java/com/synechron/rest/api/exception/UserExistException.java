package com.synechron.rest.api.exception;

public class UserExistException extends Exception{

	private static final long serialVersionUID = 1L;

	public UserExistException(String message) {
		super(message);
	}

}
