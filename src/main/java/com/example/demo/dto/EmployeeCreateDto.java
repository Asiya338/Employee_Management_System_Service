package com.example.demo.dto;

import com.example.demo.enums.DepartmentEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCreateDto {

	@NotBlank(message = "Name is mandatory")
	@Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
	private String name;

	@NotBlank(message = "Email is mandatory")
	@Email(message = "Email should be valid")
	private String email;

	@NotBlank(message = "Password is mandatory")
	@Min(8)
	@Max(30)
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\\\d)(?=.*[@$!%*#?&])[A-Za-z\\\\d@$!%*#?&]{8,}$", message = "Password "
			+ "must contain at least 8 characters, including 1 letter, 1 number, and 1 special character")
	private String password;

	@PositiveOrZero(message = "Salary must be non-negative")
	private double salary;

	@NotNull(message = "Department is mandatory")
	private DepartmentEnum department;

}
