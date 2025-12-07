package com.example.demo.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum ErrorCodeEnum {

	GENERIC_EXCEPTION("10001", "Unexpected error in Employee Management System service.."),
	RESOURCE_NOT_FOUND("10002", "Resource not found ..."),
//	BAD_REQUEST("10003", "Bad " + "Request, try with valid details"),
	DUPLICATE_EMAIL("10004", "" + "Email already exists.."),
	METHOD_ARGUMENT_INVALID("10005", "Method Argument Not Valid Exception"),
	HTTP_MESSAGE_NOT_READABLE_EXCEPTION("10006",
			"Http Message Not Readable Exception. " + "Enter input in valid format"),
	DATA_INTEGRITY_VIOLATION_EXCEPTION("10007",
			"Data Integrity Violation Exception. Must follow referencial integrity with master table.."),
	ILLEGAL_ARGUMENT_EXCEPTION("10008", "Illegal Argument Exception "),
	PROPERTY_REFERENCE_EXCEPTION("10009", "Property Reference Exception"),
	INVALID_PAGE("100010", "Page must not be negative... "), INVALID_SIZE("100011", "Size must be positive... "),
	INVALID_SORT_BY("10012", "Could not Sort by given field. Please enter valid sortBy field..  "),
	RESOURCE_WITH_ID__NOT_FOUND("10013", "Resource not found with given Id..."),
	INVALID_DEPT_ID("10014", "Department id is invalid.."), INVALID_DSGN_ID("10015", "Designation Id is invalid.."),
	INVALID_STATUS("10016", "Invalid status. Allowed values: ACTIVE, INACTIVE, TERMINATED"),
	EMPTY_INPUT("10017", "Input cannot be null or empty..."), INVALID_SEARCH("10018", "Invalid search criteria...");

	String errorCode;
	String errorMessage;

	ErrorCodeEnum(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}
