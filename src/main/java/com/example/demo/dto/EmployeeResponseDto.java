package com.example.demo.dto;

import com.example.demo.enums.DepartmentEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDto {

	private int id;

	private String name;
	private String email;

	private double salary;

	private DepartmentEnum department;

}
