package com.example.demo.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BadRequestException extends RuntimeException {
	private String errorCode;
	private String errorMessage;

	public BadRequestException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}
