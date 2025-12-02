package com.example.demo.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeResponseDto {

	private Integer id;

	private String name;
	private String email;
	private String role;

	private String employeeCode;
	private String employeeStatus;

	private Integer designationId;
	private Integer departmentId;

	private Long phoneNumber;
	private LocalDate dob;
	private Double salary;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
