package com.example.demo.exception;

public class ResourceNotFoundException extends RuntimeException {
	private String errorCode;
	private String errorMessage;

	ResourceNotFoundException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}
