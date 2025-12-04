package com.example.demo.exception;

public class BadRequestException extends RuntimeException {
	private String errorCode;
	private String errorMessage;

	BadRequestException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}
