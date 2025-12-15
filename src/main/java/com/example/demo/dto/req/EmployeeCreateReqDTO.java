package com.example.demo.dto.req;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
public class EmployeeCreateReqDTO {

	@Schema(description = "Employee Full Name", example = "John Doe")
	@NotNull(message = "Name is mandatory")
	@Size(min = 3, max = 30, message = "Name must be between 2 and 30 characters")
	private String name;

	// Role is optional (managed later by auth service)
	@Schema(description = "Employee role", example = "EMPLOYEE")
	private String role;

	@Schema(description = "Employee Date of Birth ", example = "2001-10-20")
	@NotNull(message = "Date of birth is mandatory")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Past(message = "DOB must be a past date")
	private LocalDate dob;

	@Schema(description = "Employee designation", example = "2")
	@NotNull(message = "Designation ID is mandatory")
	private Integer designationId;

	@Schema(description = "Employee department", example = "3")
	@NotNull(message = "Department ID is mandatory")
	private Integer departmentId;

	@Schema(description = "10 digits Phone Number", example = "1234567890")
	@NotNull(message = "Phone number is mandatory")
	@Pattern(regexp = "^[1-9]\\d{9}$", message = "Phone number must be exactly 10 digits")
	private String phoneNumber;

	@Schema(description = "Employee Salary", example = "100000")
	@NotNull(message = "Salary is mandatory")
	@PositiveOrZero(message = "Salary must be non-negative")
	@Min(10000)
	private Double salary;

}
