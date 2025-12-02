package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.EmployeeCreateDto;
import com.example.demo.dto.EmployeeResponseDto;
import com.example.demo.dto.EmployeeUpdateDto;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1//employees")
public class EmployeeController {

	@PostMapping()
	public ResponseEntity<EmployeeResponseDto> createEmployee(@Valid @RequestBody EmployeeCreateDto emp) {
		log.info("Creating employee with details : {} ", emp);

		return ResponseEntity.status(HttpStatus.CREATED).body(null);
	}

	@GetMapping()
	public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {
		log.info("Fetching all Employee details ");

		return null;
	}

	@GetMapping("/{empId}")
	public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable int empId) {
		log.info("Fetching employee details with emp id : {} ", empId);

		return null;
	}

	@PutMapping("/{empId}")
	public ResponseEntity<EmployeeResponseDto> updateEmployee(@Valid @RequestBody EmployeeUpdateDto empDto,
			@PathVariable int empId) {
		log.info("Updating employee details with emp id : {} ", empId);

		return null;
	}

	@DeleteMapping("/{empId}")
	public ResponseEntity<EmployeeResponseDto> deleteEmployee(@PathVariable int empId) {
		log.info("Deleting employee details with emp id : {} ", empId);

		return null;
	}

}
