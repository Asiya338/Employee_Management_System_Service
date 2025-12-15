package com.example.demo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.req.EmployeeCreateReqDTO;
import com.example.demo.dto.req.EmployeeUpdateReqDTO;
import com.example.demo.dto.res.EmployeeResponseDTO;
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

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping()
	public ResponseEntity<EmployeeResponseDTO> createEmployee(@Valid @RequestBody EmployeeCreateReqDTO employee) {
		log.info("Creating employee with details : {} ", employee);

		EmployeeResponseDTO created = employeeService.createEmployee(employee);

		URI location = URI.create("/api/v1/employees/" + created.getId());

		log.info("Employee record created with id : {} ", created.getId());

		return ResponseEntity.created(location).body(created);
	}

	@PreAuthorize("hasAuthority('EMPLOYEE_READ')")
	@GetMapping()
	public ResponseEntity<Page<EmployeeResponseDTO>> getAllEmployees(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String order) {
		log.info("Fetching employees page={}, size={}, sortBy={}, order={}", page, size, sortBy, order);

		Page<EmployeeResponseDTO> employees = employeeService.getAllEmployees(page, size, sortBy, order);

		log.info("All employees : {} ", employees);

		return ResponseEntity.status(HttpStatus.OK).body(employees);
	}

	@PreAuthorize("hasAuthority('EMPLOYEE_READ')")
	@GetMapping("/{empId}")
	public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable int empId) {
		log.info("Fetching employee details with emp id : {} ", empId);

		EmployeeResponseDTO empById = employeeService.getEmployeeById(empId);

		log.info("Fetched Employee with employee id : {} , {} ", empId, empById);

		return ResponseEntity.status(HttpStatus.OK).body(empById);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/department/{depId}")
	public ResponseEntity<List<EmployeeResponseDTO>> getEmployeesByDevId(@PathVariable int depId) {
		log.info("Fetching employees with department id : {} ", depId);

		List<EmployeeResponseDTO> employees = employeeService.getAllEmployeesByDepId(depId);

		log.info("Fetched Employees with department id : {}  ", depId, employees);

		return ResponseEntity.status(HttpStatus.OK).body(employees);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/designation/{dsgnId}")
	public ResponseEntity<List<EmployeeResponseDTO>> getEmployeesByDsgnId(@PathVariable int dsgnId) {
		log.info("Fetching employees with designation id : {} ", dsgnId);

		List<EmployeeResponseDTO> employees = employeeService.getAllEmployeesByDsgnId(dsgnId);

		log.info("Fetched Employees with designation id : {}  ", dsgnId, employees);

		return ResponseEntity.status(HttpStatus.OK).body(employees);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/role/{roleName}")
	public ResponseEntity<List<EmployeeResponseDTO>> getEmployeesByRole(@PathVariable String roleName) {
		log.info("Fetching employees with role : {} ", roleName);

		List<EmployeeResponseDTO> employees = employeeService.getAllEmployeesByRole(roleName);

		log.info("Fetched Employees with role : {}  ", roleName, employees);

		return ResponseEntity.status(HttpStatus.OK).body(employees);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/status/{status}")
	public ResponseEntity<List<EmployeeResponseDTO>> getEmployeesByStatus(@PathVariable String status) {
		log.info("Fetching employees with status : {} ", status);

		List<EmployeeResponseDTO> employees = employeeService.getAllEmployeesByStatus(status);

		log.info("Fetched Employees with status : {}  ", status, employees);

		return ResponseEntity.status(HttpStatus.OK).body(employees);
	}

	@PreAuthorize("hasAuthority('EMPLOYEE_READ')")
	@GetMapping("/search")
	public ResponseEntity<List<EmployeeResponseDTO>> searchEmployees(@RequestParam(required = false) String name,
			@RequestParam(required = false) String email, @RequestParam(required = false) String employeeCode) {

		List<EmployeeResponseDTO> employees = employeeService.searchEmployees(name, email, employeeCode);

		return ResponseEntity.ok(employees);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{empId}")
	public ResponseEntity<EmployeeResponseDTO> updateEmployee(@Valid @RequestBody EmployeeUpdateReqDTO empDto,
			@PathVariable int empId) {
		log.info("Updating employee details with emp id : {} | {}", empId, empDto);

		EmployeeResponseDTO updatedEmployee = employeeService.updateEmployeeById(empId, empDto);

		log.info("Updated Employee with employee id : {}, {}  ", empId, updatedEmployee);

		return ResponseEntity.status(HttpStatus.OK).body(updatedEmployee);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{empId}")
	public ResponseEntity<EmployeeResponseDTO> deleteEmployee(@PathVariable int empId) {
		log.info("Deleting employee details with emp id : {} ", empId);

		EmployeeResponseDTO response = employeeService.deleteEmployeeById(empId);

		log.info("Deleted Employee with employee id : {}  ", empId);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
