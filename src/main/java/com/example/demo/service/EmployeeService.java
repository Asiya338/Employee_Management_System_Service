package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.demo.dto.req.EmployeeCreateReqDTO;
import com.example.demo.dto.req.EmployeeUpdateReqDTO;
import com.example.demo.dto.res.EmployeeResponseDTO;

@Service
public interface EmployeeService {

	EmployeeResponseDTO createEmployee(EmployeeCreateReqDTO employee);

	Page<EmployeeResponseDTO> getAllEmployees(int page, int size, String sortBy, String order);

	EmployeeResponseDTO getEmployeeById(int empId);

	EmployeeResponseDTO deleteEmployeeById(int empId);

	EmployeeResponseDTO updateEmployeeById(int empId, EmployeeUpdateReqDTO empDto);

	List<EmployeeResponseDTO> getAllEmployeesByDepId(int depId);

	List<EmployeeResponseDTO> getAllEmployeesByDsgnId(int dsgnId);

	List<EmployeeResponseDTO> getAllEmployeesByRole(String roleName);

	List<EmployeeResponseDTO> getAllEmployeesByStatus(String status);

	List<EmployeeResponseDTO> searchEmployees(String name, String email, String employeeCode);

}
