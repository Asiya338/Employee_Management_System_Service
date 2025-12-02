package com.example.demo.enums;

public enum EmpStatusEnum {

	ACTIVE("active"), INACTIVE("inactive"), TERMINATED("terminated");

	String employeeStatus;

	EmpStatusEnum(String employeeStatus) {
		this.employeeStatus = employeeStatus;
	}

	String getEmployeeStatus() {
		return employeeStatus;
	}

}
