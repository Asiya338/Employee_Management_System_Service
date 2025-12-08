package com.example.demo.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeUpdateDto {

	@Schema(description = "Employee Full Name", example = "John Doe")
	@Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
	private String name;

	@Schema(description = "Employee date of birth", example = "2009-09-28")
	@Past(message = "DOB must be a past date")
	private LocalDate dob;

	@Schema(description = "Designation Id", example = "2")
	private Integer designationId;

	@Schema(description = "Department Id", example = "3")
	private Integer departmentId;

	@Schema(description = "10 digits Phone number", example = "1234567890")
	@Pattern(regexp = "^[1-9]\\d{9}$", message = "Phone number must be exactly 10 digits")
	private String phoneNumber;

	@Schema(description = "Employee Salary", example = "12000")
	@PositiveOrZero(message = "Salary must be non-negative")
	private Double salary;

	@Schema(description = "Is Deleted flag", example = "false")
	private Boolean isDeleted;
}
