package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.dto.EmployeeCreateDto;
import com.example.demo.dto.EmployeeResponseDto;
import com.example.demo.entity.Employee;
import com.example.demo.repo.EmployeeRepo;
import com.example.demo.service.EmployeeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepo employeeRepo;
	private final ModelMapper modelMapper;

	@Override
	public EmployeeResponseDto createEmployee(EmployeeCreateDto employeeDto) {
		log.info("Employee DTO || createEmployee : {} ", employeeDto);

		Employee employee = modelMapper.map(employeeDto, Employee.class);

		employee.setCreatedAt(LocalDateTime.now());
		employee.setJoinedAt(LocalDateTime.now());
		employee.setUpdatedAt(LocalDateTime.now());
		employee.setEmployeeCode("em67p");

		employeeRepo.save(employee);

		Employee savedEmployee = employeeRepo.save(employee);
		String empCode = String.format("EMP%05d", savedEmployee.getId());
		savedEmployee.setEmployeeCode(empCode);

		employee = employeeRepo.save(savedEmployee);
		EmployeeResponseDto response = modelMapper.map(employee, EmployeeResponseDto.class);

		log.info("Employee created : {} ", response);

		return response;
	}

	@Override
	public Page<EmployeeResponseDto> getAllEmployees(int page, int size, String sortBy, String order) {
		Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

		Page<Employee> employeePage = employeeRepo.findAll(pageable);

		Page<EmployeeResponseDto> response = employeePage
				.map(employee -> modelMapper.map(employee, EmployeeResponseDto.class));
		return response;
	}

	@Override
	public EmployeeResponseDto getEmployeeById(int empId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteEmployeeById(int empId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmployeeResponseDto updateEmployeeById(int empId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmployeeResponseDto> getAllEmployeesByDepId(int depId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmployeeResponseDto> getAllEmployeesByDsgnId(int dsgnId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmployeeResponseDto> getAllEmployeesByRole(String roleName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmployeeResponseDto> getAllEmployeesByStatus(String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmployeeResponseDto> searchEmployees(String name, String email, String employeeCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
