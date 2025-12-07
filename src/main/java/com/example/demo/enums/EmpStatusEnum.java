package com.example.demo.enums;

import lombok.Getter;

@Getter
public enum EmpStatusEnum {

	ACTIVE("active"), INACTIVE("inactive"), TERMINATED("terminated");

	String employeeStatus;

	EmpStatusEnum(String employeeStatus) {
		this.employeeStatus = employeeStatus;
	}
}
