package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.dto.EmployeeCreateDto;
import com.example.demo.dto.EmployeeResponseDto;

public interface EmployeeService {

	EmployeeResponseDto createEmployee(EmployeeCreateDto employee);

	Page<EmployeeResponseDto> getAllEmployees(int page, int size, String sortBy, String order);

	EmployeeResponseDto getEmployeeById(int empId);

	String deleteEmployeeById(int empId);

	EmployeeResponseDto updateEmployeeById(int empId);

	List<EmployeeResponseDto> getAllEmployeesByDepId(int depId);

	List<EmployeeResponseDto> getAllEmployeesByDsgnId(int dsgnId);

	List<EmployeeResponseDto> getAllEmployeesByRole(String roleName);

	List<EmployeeResponseDto> getAllEmployeesByStatus(String status);

}
