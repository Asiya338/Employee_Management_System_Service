package com.example.demo.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum ErrorCodeEnum {

	GENERIC_EXCEPTION("30001", "Unexpected error in Employee Management System service.."),
	RESOURCE_NOT_FOUND("30002", "Resource not found ..."),
//	BAD_REQUEST("10003", "Bad " + "Request, try with valid details"),
	METHOD_ARGUMENT_INVALID("30005", "Method Argument Not Valid Exception"),
	HTTP_MESSAGE_NOT_READABLE_EXCEPTION("30006",
			"Http Message Not Readable Exception. " + "Enter input in valid format"),
	DATA_INTEGRITY_VIOLATION_EXCEPTION("30007",
			"Data Integrity Violation Exception. Must follow referencial integrity with master table.."),
	ILLEGAL_ARGUMENT_EXCEPTION("30008", "Illegal Argument Exception "),
	PROPERTY_REFERENCE_EXCEPTION("30009", "Property Reference Exception"),
	INVALID_PAGE("30010", "Page must not be negative... "), INVALID_SIZE("30011", "Size must be positive... "),
	INVALID_SORT_BY("30012", "Could not Sort by given field. Please enter valid sortBy field..  "),
	RESOURCE_WITH_ID__NOT_FOUND("30013", "Resource not found with given Id..."),
	INVALID_DEPT_ID("30014", "Department id is invalid.."), INVALID_DSGN_ID("30015", "Designation Id is invalid.."),
	INVALID_STATUS("30016", "Invalid status. Allowed values: ACTIVE, INACTIVE, TERMINATED"),
	EMPTY_INPUT("30017", "Input cannot be null or empty..."), INVALID_SEARCH("30018", "Invalid search criteria...");

	String errorCode;
	String errorMessage;

	ErrorCodeEnum(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}
