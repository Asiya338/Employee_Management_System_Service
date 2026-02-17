package com.example.demo.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthServiceUnavailableException extends RuntimeException {

	private static final long serialVersionUID = 6786383000988321777L;
	private String errorCode;
	private String errorMessage;

	public AuthServiceUnavailableException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}
