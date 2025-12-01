package com.example.demo.enums;

import lombok.Getter;

@Getter
public enum DepartmentEnum {

	HR("Human Resources"), FINANCE("Finance Department"),

	SALES("Sales Department"), MARKETING("Marketing Department"), IT("IT Department");

	String departmentName;

	DepartmentEnum(String departmentName) {
		this.departmentName = departmentName;
	}
}
