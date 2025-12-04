package com.example.demo.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ValidationException extends RuntimeException {
	private String errorCode;
	private String errorMessage;

	ValidationException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}
