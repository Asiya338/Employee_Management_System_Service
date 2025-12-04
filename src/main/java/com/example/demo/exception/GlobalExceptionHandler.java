package com.example.demo.exception;

import java.time.LocalDateTime;

import org.slf4j.MDC;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.constants.Constant;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> resourceNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
		log.info("Resource not found exception occured : {} ", ex.getMessage(), ex);

		ErrorResponse response = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage(), req.getRequestURI(),
				MDC.get(Constant.traceId), LocalDateTime.now(), HttpMethod.valueOf(req.getMethod()));

		log.info("ResourceNotFoundException || Error response : {} ", response);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponse> badRequest(BadRequestException ex, HttpServletRequest req) {
		log.info("Bad Request Exception occured : {} ", ex.getMessage(), ex);

		ErrorResponse response = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage(), req.getRequestURI(),
				MDC.get(Constant.traceId), LocalDateTime.now(), HttpMethod.valueOf(req.getMethod()));

		log.info("BadRequestException || Error response : {} ", response);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ErrorResponse> duplicateRequest(DuplicateResourceException ex, HttpServletRequest req) {
		log.info("Duplicate Resource Exception occured : {} ", ex.getMessage(), ex);

		ErrorResponse response = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage(), req.getRequestURI(),
				MDC.get(Constant.traceId), LocalDateTime.now(), HttpMethod.valueOf(req.getMethod()));

		log.info("DuplicateResourceException || Error response : {} ", response);

		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ErrorResponse> validationError(ValidationException ex, HttpServletRequest req) {
		log.info("Validation Exception occured : {} ", ex.getMessage(), ex);

		ErrorResponse response = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage(), req.getRequestURI(),
				MDC.get(Constant.traceId), LocalDateTime.now(), HttpMethod.valueOf(req.getMethod()));

		log.info("ValidationException || Error response : {} ", response);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<ErrorResponse> genericException(ApplicationException ex, HttpServletRequest req) {
		log.info("Application Exception occured : {} ", ex.getMessage(), ex);

		ErrorResponse response = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage(), req.getRequestURI(),
				MDC.get(Constant.traceId), LocalDateTime.now(), HttpMethod.valueOf(req.getMethod()));

		log.info("Application Exception || Error response : {} ", response);

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
}
