package com.project.util.exception;

public class BaseSystemException extends RuntimeException {

	public BaseSystemException() {
		super();
	}
	
	public BaseSystemException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public BaseSystemException(String message) {
		super(message);
	}
	
	public BaseSystemException(Throwable cause) {
		super(cause);
	}
	
}