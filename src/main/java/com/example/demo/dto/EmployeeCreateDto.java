package com.example.demo.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeCreateDto {

	@NotBlank(message = "Name is mandatory")
	@Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
	private String name;

	@NotBlank(message = "Email is mandatory")
	@Email(message = "Email should be valid")
	private String email;

	// Role is optional (managed later by auth service)
	private String role;

	@NotNull(message = "Date of birth is mandatory")
	@Past(message = "DOB must be a past date")
	private LocalDate dob;

	@NotNull(message = "Designation ID is mandatory")
	private Integer designationId;

	@NotNull(message = "Department ID is mandatory")
	private Integer departmentId;

	@NotNull(message = "Phone number is mandatory")
	@Digits(integer = 10, fraction = 0, message = "Phone number must be exactly 10 digits")
	private Long phoneNumber;

	@NotNull(message = "Salary is mandatory")
	@PositiveOrZero(message = "Salary must be non-negative")
	private Double salary;

}
