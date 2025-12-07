package com.example.demo.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeResponseDto {

	@Schema(description = "Employee ID", example = "23")
	private Integer id;

	@Schema(description = "Employee full name", example = "John Doe")
	private String name;

	@Schema(description = "Employee email address", example = "johndoe123@gmail.com")
	private String email;

	@Schema(description = "Employee Role", example = "EMPLOYEE")
	private String role;

	@Schema(description = "Generated Employee Code", example = "EMP00012")
	private String employeeCode;

	@Schema(description = "Employee status", example = "ACTIVE")
	private String employeeStatus;

	@Schema(description = "Designation ID", example = "1")
	private Integer designationId;

	@Schema(description = "Department ID", example = "2")
	private Integer departmentId;

	@Schema(description = "10 digits Phone Number", example = "1234567890")
	private Long phoneNumber;

	@Schema(description = "Employee Date of Birth", example = "2002-10-20")
	private LocalDate dob;

	@Schema(description = "Employee Salary", example = "27500")
	private Double salary;

	@Schema(description = "Employee created at", example = "2025-12-04 16:18:21.52")
	private LocalDateTime createdAt;

	@Schema(description = "Employee details updated at ", example = "2025-12-05 12:09:11.12")
	private LocalDateTime updatedAt;

	@Schema(description = "Employee deleted at", example = "2026-10-14 21:11:11.12")
	private LocalDateTime deletedAt;

}
