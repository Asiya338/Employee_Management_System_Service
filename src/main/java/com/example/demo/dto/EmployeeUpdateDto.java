package com.example.demo.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Digits;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeUpdateDto {

	@Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
	private String name;

	@Past(message = "DOB must be a past date")
	private LocalDate dob;

	private Integer designationId;

	private Integer departmentId;

	@Digits(integer = 10, fraction = 0, message = "Phone number must be exactly 10 digits")
	private Long phoneNumber;

	@PositiveOrZero(message = "Salary must be non-negative")
	private Double salary;
}
