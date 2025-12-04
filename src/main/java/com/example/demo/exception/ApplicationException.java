package com.example.demo.exception;

public class ApplicationException extends RuntimeException {
	private String errorCode;
	private String errorMessage;

	ApplicationException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}
