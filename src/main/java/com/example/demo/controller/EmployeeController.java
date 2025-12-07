package com.example.demo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.EmployeeCreateDto;
import com.example.demo.dto.EmployeeResponseDto;
import com.example.demo.dto.EmployeeUpdateDto;
import com.example.demo.service.EmployeeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

	private final EmployeeService employeeService;

	@PostMapping()
	public ResponseEntity<EmployeeResponseDto> createEmployee(@Valid @RequestBody EmployeeCreateDto employee) {
		log.info("Creating employee with details : {} ", employee);

		EmployeeResponseDto created = employeeService.createEmployee(employee);

		URI location = URI.create("/api/v1/employees/" + created.getId());

		log.info("Employee record created with id : {} ", created.getId());

		return ResponseEntity.created(location).body(created);
	}

	@GetMapping()
	public ResponseEntity<Page<EmployeeResponseDto>> getAllEmployees(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String order) {
		log.info("Fetching employees page={}, size={}, sortBy={}, order={}", page, size, sortBy, order);

		Page<EmployeeResponseDto> employees = employeeService.getAllEmployees(page, size, sortBy, order);

		log.info("All employees : {} ", employees);

		return ResponseEntity.status(HttpStatus.OK).body(employees);
	}

	@GetMapping("/{empId}")
	public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable int empId) {
		log.info("Fetching employee details with emp id : {} ", empId);

		EmployeeResponseDto empById = employeeService.getEmployeeById(empId);

		log.info("Fetched Employee with employee id : {} , {} ", empId, empById);

		return ResponseEntity.status(HttpStatus.OK).body(empById);
	}

	@GetMapping("/department/{depId}")
	public ResponseEntity<List<EmployeeResponseDto>> getEmployeesByDevId(@PathVariable int depId) {
		log.info("Fetching employees with department id : {} ", depId);

		List<EmployeeResponseDto> employees = employeeService.getAllEmployeesByDepId(depId);

		log.info("Fetched Employees with department id : {}  ", depId, employees);

		return ResponseEntity.status(HttpStatus.OK).body(employees);
	}

	@GetMapping("/designation/{dsgnId}")
	public ResponseEntity<List<EmployeeResponseDto>> getEmployeesByDsgnId(@PathVariable int dsgnId) {
		log.info("Fetching employees with designation id : {} ", dsgnId);

		List<EmployeeResponseDto> employees = employeeService.getAllEmployeesByDsgnId(dsgnId);

		log.info("Fetched Employees with designation id : {}  ", dsgnId, employees);

		return ResponseEntity.status(HttpStatus.OK).body(employees);
	}

	@GetMapping("/role/{roleName}")
	public ResponseEntity<List<EmployeeResponseDto>> getEmployeesByRole(@PathVariable String roleName) {
		log.info("Fetching employees with role : {} ", roleName);

		List<EmployeeResponseDto> employees = employeeService.getAllEmployeesByRole(roleName);

		log.info("Fetched Employees with role : {}  ", roleName, employees);

		return ResponseEntity.status(HttpStatus.OK).body(employees);
	}

	@GetMapping("/status/{status}")
	public ResponseEntity<List<EmployeeResponseDto>> getEmployeesByStatus(@PathVariable String status) {
		log.info("Fetching employees with status : {} ", status);

		List<EmployeeResponseDto> employees = employeeService.getAllEmployeesByStatus(status);

		log.info("Fetched Employees with status : {}  ", status, employees);

		return ResponseEntity.status(HttpStatus.OK).body(employees);
	}

	@GetMapping("/search")
	public ResponseEntity<List<EmployeeResponseDto>> searchEmployees(@RequestParam(required = false) String name,
			@RequestParam(required = false) String email, @RequestParam(required = false) String employeeCode) {

		List<EmployeeResponseDto> employees = employeeService.searchEmployees(name, email, employeeCode);

		return ResponseEntity.ok(employees);
	}

	@PutMapping("/{empId}")
	public ResponseEntity<EmployeeResponseDto> updateEmployee(@Valid @RequestBody EmployeeUpdateDto empDto,
			@PathVariable int empId) {
		log.info("Updating employee details with emp id : {} | {}", empId, empDto);

		EmployeeResponseDto updatedEmployee = employeeService.updateEmployeeById(empId, empDto);

		log.info("Updated Employee with employee id : {}, {}  ", empId, updatedEmployee);

		return ResponseEntity.status(HttpStatus.OK).body(updatedEmployee);
	}

	@DeleteMapping("/{empId}")
	public ResponseEntity<EmployeeResponseDto> deleteEmployee(@PathVariable int empId) {
		log.info("Deleting employee details with emp id : {} ", empId);

		EmployeeResponseDto response = employeeService.deleteEmployeeById(empId);

		log.info("Deleted Employee with employee id : {}  ", empId);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
