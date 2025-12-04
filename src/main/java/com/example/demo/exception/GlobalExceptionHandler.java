package com.example.demo.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import org.slf4j.MDC;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.constants.Constant;
import com.example.demo.enums.ErrorCodeEnum;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> resourceNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
		log.info("Resource not found exception occured : {} ", ex.getMessage(), ex);

		ErrorResponse response = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage(), req.getRequestURI(),
				MDC.get(Constant.traceId), LocalDateTime.now(), req.getMethod());

		log.info("ResourceNotFoundException || Error response : {} ", response);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponse> badRequest(BadRequestException ex, HttpServletRequest req) {
		log.info("Bad Request Exception occured : {} ", ex.getMessage(), ex);

		ErrorResponse response = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage(), req.getRequestURI(),
				MDC.get(Constant.traceId), LocalDateTime.now(), req.getMethod());

		log.info("BadRequestException || Error response : {} ", response);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ErrorResponse> duplicateRequest(DuplicateResourceException ex, HttpServletRequest req) {
		log.info("Duplicate Resource Exception occured : {} ", ex.getMessage(), ex);

		ErrorResponse response = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage(), req.getRequestURI(),
				MDC.get(Constant.traceId), LocalDateTime.now(), req.getMethod());

		log.info("DuplicateResourceException || Error response : {} ", response);

		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> validationError(MethodArgumentNotValidException ex, HttpServletRequest req) {
		log.info("Validation Exception occured : {} ", ex.getMessage(), ex);

		String msg = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).findFirst()
				.orElse("Invalid input");

		ErrorResponse response = new ErrorResponse(ErrorCodeEnum.METHOD_ARGUMENT_INVALID.getErrorCode(), msg,
				req.getRequestURI(), MDC.get(Constant.traceId), LocalDateTime.now(), req.getMethod());

		log.info("ValidationException || Error response : {} ", response);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleJsonParseError(HttpMessageNotReadableException ex,
			HttpServletRequest req) {
		String message = ex.getMessage();

		if (ex.getCause() instanceof DateTimeParseException) {
			message = "Invalid date format. Expected yyyy-MM-dd, e.g., 2003-02-15";
		}

		ErrorResponse response = new ErrorResponse(ErrorCodeEnum.HTTP_MESSAGE_NOT_READABLE_EXCEPTION.getErrorCode(),
				message, req.getRequestURI(), MDC.get(Constant.traceId), LocalDateTime.now(), req.getMethod());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex,
			HttpServletRequest req) {

		String message = "Data integrity violation";

		if (ex.getMessage() != null && ex.getMessage().contains("emp_designation_fk")) {
			message = "Invalid designationId: No such designation exists";
		}

		else if (ex.getMessage() != null && ex.getMessage().contains("emp_department_fk")) {
			message = "Invalid departmentId: No such department exists";
		}
		ErrorResponse response = new ErrorResponse(ErrorCodeEnum.DATA_INTEGRITY_VIOLATION_EXCEPTION.getErrorCode(),
				message, req.getRequestURI(), MDC.get(Constant.traceId), LocalDateTime.now(), req.getMethod());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> illegalArgsRequest(IllegalArgumentException ex, HttpServletRequest req) {
		log.info("IllegalArgumentException occured : {} ", ex.getMessage(), ex);

		ErrorResponse response = new ErrorResponse(ErrorCodeEnum.ILLEGAL_ARGUMENT_EXCEPTION.getErrorCode(),
				ex.getMessage(), req.getRequestURI(), MDC.get(Constant.traceId), LocalDateTime.now(), req.getMethod());

		log.info("IllegalArgumentException || Error response : {} ", response);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(PropertyReferenceException.class)
	public ResponseEntity<ErrorResponse> propertyReferenceException(PropertyReferenceException ex,
			HttpServletRequest req) {
		log.info("PropertyReferenceException occured : {} ", ex.getMessage(), ex);

		ErrorResponse response = new ErrorResponse(ErrorCodeEnum.PROPERTY_REFERENCE_EXCEPTION.getErrorCode(),
				ex.getMessage(), req.getRequestURI(), MDC.get(Constant.traceId), LocalDateTime.now(), req.getMethod());

		log.info("PropertyReferenceException || Error response : {} ", response);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> genericException(Exception ex, HttpServletRequest req) {
		log.info("Application Exception occured : {} ", ex.getMessage(), ex);

		ErrorResponse response = new ErrorResponse(ErrorCodeEnum.GENERIC_EXCEPTION.getErrorCode(), ex.getMessage(),
				req.getRequestURI(), MDC.get(Constant.traceId), LocalDateTime.now(), req.getMethod());

		log.info("Application Exception || Error response : {} ", response);

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
}
