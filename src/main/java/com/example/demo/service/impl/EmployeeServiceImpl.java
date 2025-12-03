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
import com.example.demo.enums.EmpStatusEnum;
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
		log.info("get all employees on page:{}, size:{}, sortBy:{}, order:{}", page, size, sortBy, order);

		Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

		Page<Employee> employeePage = employeeRepo.findAll(pageable);

		Page<EmployeeResponseDto> response = employeePage
				.map(employee -> modelMapper.map(employee, EmployeeResponseDto.class));

		log.info("All employees || getAllEmployees : {} ", response);

		return response;
	}

	@Override
	public EmployeeResponseDto getEmployeeById(int empId) {
		log.info("Employee with id : {} ", empId);

		Employee empById = employeeRepo.findById(empId).orElseThrow(null);

		EmployeeResponseDto response = modelMapper.map(empById, EmployeeResponseDto.class);

		log.info("Employee details with id : {} ", empId);

		return response;

	}

	@Override
	public String deleteEmployeeById(int empId) {
		log.info("Deleting Employee with id : {} ", empId);

		employeeRepo.deleteById(empId);

		log.info("deleted Employee with id : {} ", empId);

		return "Deleted Successfuly";

	}

	@Override
	public List<EmployeeResponseDto> getAllEmployeesByDepId(int depId) {
		log.info("Get all employees from department : {}", depId);

		List<Employee> employees = employeeRepo.findByDepartmentId(depId);

		List<EmployeeResponseDto> responseDto = employees.stream()
				.map(employee -> modelMapper.map(employee, EmployeeResponseDto.class)).toList();

		log.info("Employees from department : {} , {}", depId, responseDto);

		return responseDto;
	}

	@Override
	public List<EmployeeResponseDto> getAllEmployeesByDsgnId(int dsgnId) {
		log.info("Get all employees from designation : {}", dsgnId);

		List<Employee> employees = employeeRepo.findByDesignationId(dsgnId);

		List<EmployeeResponseDto> responseDto = employees.stream()
				.map(employee -> modelMapper.map(employee, EmployeeResponseDto.class)).toList();

		log.info("Employees from designation : {} , {}", dsgnId, responseDto);

		return responseDto;
	}

	@Override
	public List<EmployeeResponseDto> getAllEmployeesByRole(String role) {
		log.info("Get all employees by role: {}", role);

		List<Employee> employees = employeeRepo.findByRole(role);

		List<EmployeeResponseDto> responseDto = employees.stream()
				.map(employee -> modelMapper.map(employee, EmployeeResponseDto.class)).toList();

		log.info("Employees by role : {} , {}", role, responseDto);

		return responseDto;
	}

	@Override
	public List<EmployeeResponseDto> getAllEmployeesByStatus(String status) {
		log.info("Get all employees by employee status: {}", status);

		EmpStatusEnum enumStatus;
		try {
			enumStatus = EmpStatusEnum.valueOf(status.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Invalid status. Allowed values: ACTIVE, INACTIVE, TERMINATED");
		}

		List<Employee> employees = employeeRepo.findByEmployeeStatus(enumStatus);

		List<EmployeeResponseDto> responseDto = employees.stream()
				.map(employee -> modelMapper.map(employee, EmployeeResponseDto.class)).toList();

		log.info("Employees by status : {} , {}", status, responseDto);

		return responseDto;

	}

	@Override
	public List<EmployeeResponseDto> searchEmployees(String name, String email, String employeeCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmployeeResponseDto updateEmployeeById(int empId) {
		// TODO Auto-generated method stub
		return null;
	}

}
