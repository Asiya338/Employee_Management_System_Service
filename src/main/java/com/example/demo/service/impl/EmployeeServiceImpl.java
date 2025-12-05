package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.constants.Constant;
import com.example.demo.dto.EmployeeCreateDto;
import com.example.demo.dto.EmployeeDelDto;
import com.example.demo.dto.EmployeeResponseDto;
import com.example.demo.dto.EmployeeUpdateDto;
import com.example.demo.entity.Employee;
import com.example.demo.enums.EmpStatusEnum;
import com.example.demo.enums.ErrorCodeEnum;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
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
		log.info("Employee Create DTO : {} ", employeeDto);

		if (employeeRepo.findByEmail(employeeDto.getEmail()) != null) {
			throw new DuplicateResourceException(ErrorCodeEnum.DUPLICATE_EMAIL.getErrorCode(),
					ErrorCodeEnum.DUPLICATE_EMAIL.getErrorMessage());
		}

		Employee employee = modelMapper.map(employeeDto, Employee.class);

		employee.setCreatedAt(LocalDateTime.now());
		employee.setJoinedAt(LocalDateTime.now());
		employee.setUpdatedAt(LocalDateTime.now());
		employee.setEmployeeCode(Constant.dummyEmpCode);

		Employee savedEmployee = employeeRepo.save(employee);
		String empCode = String.format(Constant.formatEmpCode, savedEmployee.getId());
		savedEmployee.setEmployeeCode(empCode);

		employee = employeeRepo.save(savedEmployee);
		EmployeeResponseDto response = modelMapper.map(employee, EmployeeResponseDto.class);

		log.info("Employee created : {} ", response);

		return response;
	}

	@Override
	public Page<EmployeeResponseDto> getAllEmployees(int page, int size, String sortBy, String order) {
		log.info("get all employees on page:{}, size:{}, sortBy:{}, order:{}", page, size, sortBy, order);

		if (page < 0) {
			throw new BadRequestException(ErrorCodeEnum.INVALID_PAGE.getErrorCode(),
					ErrorCodeEnum.INVALID_PAGE.getErrorMessage());
		} else if (size <= 0) {
			throw new BadRequestException(ErrorCodeEnum.INVALID_SIZE.getErrorCode(),
					ErrorCodeEnum.INVALID_SIZE.getErrorMessage());
		} else if (!Constant.ALLOWED_SORT_FIELDS.contains(sortBy)) {
			throw new BadRequestException(ErrorCodeEnum.INVALID_SORT_BY.getErrorCode(),
					ErrorCodeEnum.INVALID_SORT_BY.getErrorMessage() + ": " + sortBy);
		}

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

		Employee empById = employeeRepo.findById(empId).orElseThrow(
				() -> new ResourceNotFoundException(ErrorCodeEnum.RESOURCE_WITH_ID__NOT_FOUND.getErrorCode(),
						ErrorCodeEnum.RESOURCE_WITH_ID__NOT_FOUND.getErrorMessage() + " || No such employee with Id : "
								+ empId));

		EmployeeResponseDto response = modelMapper.map(empById, EmployeeResponseDto.class);

		log.info("Employee details with id : {} ", empId);

		return response;
	}

	@Override
	public EmployeeDelDto deleteEmployeeById(int empId) {
		log.info("Deleting Employee with id : {} ", empId);

		Employee empById = employeeRepo.findById(empId).orElseThrow(
				() -> new ResourceNotFoundException(ErrorCodeEnum.RESOURCE_WITH_ID__NOT_FOUND.getErrorCode(),
						ErrorCodeEnum.RESOURCE_WITH_ID__NOT_FOUND.getErrorMessage()
								+ "||Unable to delete employee with Id : " + empId));

		employeeRepo.delete(empById);

		log.info("deleted Employee with id : {} ", empId);

		return new EmployeeDelDto(empId, LocalDateTime.now());

	}

	@Override
	public List<EmployeeResponseDto> getAllEmployeesByDepId(int depId) {
		log.info("Get all employees from department : {}", depId);

		if (!Constant.ALLOWED_DEPT_ID.contains(depId)) {
			throw new BadRequestException(ErrorCodeEnum.INVALID_DEPT_ID.getErrorCode(),
					ErrorCodeEnum.INVALID_DEPT_ID.getErrorMessage() + " Allowed values: " + Constant.ALLOWED_DEPT_ID);
		}

		List<Employee> employees = employeeRepo.findByDepartmentId(depId);

		List<EmployeeResponseDto> responseDto = employees.stream()
				.map(employee -> modelMapper.map(employee, EmployeeResponseDto.class)).toList();

		log.info("Employees from department : {} , {}", depId, responseDto);

		return responseDto;
	}

	@Override
	public List<EmployeeResponseDto> getAllEmployeesByDsgnId(int dsgnId) {
		log.info("Get all employees from designation : {}", dsgnId);

		if (!Constant.ALLOWED_DSGN_ID.contains(dsgnId)) {
			throw new BadRequestException(ErrorCodeEnum.INVALID_DSGN_ID.getErrorCode(),
					ErrorCodeEnum.INVALID_DSGN_ID.getErrorMessage() + " Allowed values: " + Constant.ALLOWED_DSGN_ID);
		}

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
			throw new BadRequestException(ErrorCodeEnum.INVALID_STATUS.getErrorCode(),
					ErrorCodeEnum.INVALID_STATUS.getErrorMessage());
		}

		List<Employee> employees = employeeRepo.findByEmployeeStatus(enumStatus);

		List<EmployeeResponseDto> responseDto = employees.stream()
				.map(employee -> modelMapper.map(employee, EmployeeResponseDto.class)).toList();

		log.info("Employees by status : {} , {}", status, responseDto);

		return responseDto;
	}

	@Override
	public List<EmployeeResponseDto> searchEmployees(String name, String email, String employeeCode) {
		log.info("Searching employee/s based on employeeCode : {} , or email : {} , or name : {} ...", employeeCode,
				email, name);

		if (employeeCode != null) {
			Employee employee = employeeRepo.findByEmployeeCode(employeeCode);
			if (employee != null) {
				log.info("Employee with employeeCode: {}  ", employeeCode);

				return List.of(modelMapper.map(employee, EmployeeResponseDto.class));
			}
		} else if (email != null) {
			Employee employee = employeeRepo.findByEmail(email);
			if (employee != null) {
				log.info("Employee with email: {}  ", email);

				return List.of(modelMapper.map(employee, EmployeeResponseDto.class));
			}
		} else if (name != null) {
			List<Employee> employees = employeeRepo.findByNameContainingIgnoreCase(name);
			if (!employees.isEmpty()) {
				log.info("there are {} Employee with name: {}  ", employees.size(), name);

				List<EmployeeResponseDto> response = employees.stream()
						.map(employee -> modelMapper.map(employee, EmployeeResponseDto.class)).toList();
				return response;
			}
		}
		log.info("There are no such employees with given employeeCode : {}  |  email : {}  |  name : {}  ",
				employeeCode, email, name);

		return List.of();
	}

	@Override
	public EmployeeResponseDto updateEmployeeById(int empId, EmployeeUpdateDto empDto) {
		log.info("Updating employee with employee id: {} | employee update dto : {} ", empId, empDto);

		Employee employee = employeeRepo.findById(empId).orElseThrow(
				() -> new ResourceNotFoundException(ErrorCodeEnum.RESOURCE_WITH_ID__NOT_FOUND.getErrorCode(),
						ErrorCodeEnum.RESOURCE_WITH_ID__NOT_FOUND.getErrorMessage()
								+ " || Failed to Update employee with ID:  " + empId));

		modelMapper.map(empDto, employee);

		employee.setUpdatedAt(LocalDateTime.now());
		Employee savedEmployee = employeeRepo.save(employee);

		EmployeeResponseDto response = modelMapper.map(savedEmployee, EmployeeResponseDto.class);

		log.info("Employee updated with id {} | employee :  {} ", empId, response);

		return response;
	}

}
