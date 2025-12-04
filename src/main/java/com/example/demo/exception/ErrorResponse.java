package com.example.demo.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpMethod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

	private String errorCode;
	private String errorMessage;
	private String path;
	private String traceId;

	private LocalDateTime timeStamp;

	private HttpMethod httpMethod;
}
