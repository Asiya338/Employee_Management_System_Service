package com.example.demo.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 6786383000988321777L;
	private String errorCode;
	private String errorMessage;

	public BadRequestException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}
