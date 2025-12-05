package com.example.demo.exception;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException {
	private String errorCode;
	private String errorMessage;

	public ResourceNotFoundException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}
