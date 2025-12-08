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
import com.example.demo.dto.EmployeeResponseDto;
import com.example.demo.dto.EmployeeUpdateDto;
import com.example.demo.entity.Employee;
import com.example.demo.enums.EmpStatusEnum;
import com.example.demo.enums.ErrorCodeEnum;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.http.DepartmentClient;
import com.example.demo.http.DesignationClient;
import com.example.demo.repo.EmployeeRepo;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.impl.helper.EmployeeServiceHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepo employeeRepo;
	private final ModelMapper modelMapper;
	private final DepartmentClient departmentClient;
	private final DesignationClient designationClient;
	private final EmployeeServiceHelper employeeServiceHelper;

	@Override
	public EmployeeResponseDto createEmployee(EmployeeCreateDto employeeDto) {
		log.info("Employee Create DTO : {} ", employeeDto);

		if (!departmentClient.isValidDepartment(employeeDto.getDepartmentId())) {
			log.error("Department is not valid : {} ", employeeDto.getDepartmentId());

			throw new BadRequestException(ErrorCodeEnum.INVALID_DEPT_ID.getErrorCode(),
					ErrorCodeEnum.INVALID_DEPT_ID.getErrorMessage() + " : " + employeeDto.getDepartmentId());
		}

		if (!designationClient.isValidDesignation(employeeDto.getDesignationId())) {
			log.error("Designation is not valid : {} ", employeeDto.getDesignationId());

			throw new BadRequestException(ErrorCodeEnum.INVALID_DSGN_ID.getErrorCode(),
					ErrorCodeEnum.INVALID_DSGN_ID.getErrorMessage() + " : " + employeeDto.getDesignationId());
		}

		Employee employee = modelMapper.map(employeeDto, Employee.class);
		employee.setEmployeeCode(Constant.dummyEmpCode);
		employee.setEmail(Constant.dummyEmail);

		Employee savedEmployee = employeeRepo.save(employee);

		String empCode = String.format(Constant.formatEmpCode, savedEmployee.getId());
		savedEmployee.setEmployeeCode(empCode);
		savedEmployee.setEmail(employeeServiceHelper.generateCompanyEmail(employeeDto.getName(), empCode));

		employee = employeeRepo.save(savedEmployee);
		EmployeeResponseDto response = modelMapper.map(employee, EmployeeResponseDto.class);

		log.info("Employee created : {} ", response);

		return response;
	}

	@Override
	public Page<EmployeeResponseDto> getAllEmployees(int page, int size, String sortBy, String order) {
		log.info("get all employees on page:{}, size:{}, sortBy:{}, order:{}", page, size, sortBy, order);

		if (page < 0) {
			log.error("Invalid page number || getAllEmployees : {} ", page);

			throw new BadRequestException(ErrorCodeEnum.INVALID_PAGE.getErrorCode(),
					ErrorCodeEnum.INVALID_PAGE.getErrorMessage());
		} else if (size <= 0) {
			log.error("Invalid size || getAllEmployees : {} ", size);

			throw new BadRequestException(ErrorCodeEnum.INVALID_SIZE.getErrorCode(),
					ErrorCodeEnum.INVALID_SIZE.getErrorMessage());
		} else if (!Constant.ALLOWED_SORT_FIELDS.contains(sortBy)) {
			log.error("Invalid sortBy field || getAllEmployees : {} ", sortBy);

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
	public EmployeeResponseDto deleteEmployeeById(int empId) {
		log.info("Deleting Employee with id : {} ", empId);

		Employee empById = employeeRepo.findById(empId).orElseThrow(
				() -> new ResourceNotFoundException(ErrorCodeEnum.RESOURCE_WITH_ID__NOT_FOUND.getErrorCode(),
						ErrorCodeEnum.RESOURCE_WITH_ID__NOT_FOUND.getErrorMessage()
								+ "||Unable to delete employee with Id : " + empId));

		empById.setIsDeleted(true);
		employeeRepo.save(empById);

		log.info("deleted Employee with id : {} ", empId);

		EmployeeResponseDto response = new EmployeeResponseDto();
		response.setId(empId);
		response.setDeletedAt(LocalDateTime.now());
		response.setIsDeleted(true);

		log.error("Employee deleted : {} ", response);

		return response;
	}

	@Override
	public List<EmployeeResponseDto> getAllEmployeesByDepId(int depId) {
		log.info("Get all employees from department : {}", depId);

		if (!departmentClient.isValidDepartment(depId)) {
			log.error("Department is not valid : {} ", depId);

			throw new BadRequestException(ErrorCodeEnum.INVALID_DEPT_ID.getErrorCode(),
					ErrorCodeEnum.INVALID_DEPT_ID.getErrorMessage() + " : " + depId);
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

		if (!designationClient.isValidDesignation(dsgnId)) {
			log.error("Designation is not valid : {} ", dsgnId);

			throw new BadRequestException(ErrorCodeEnum.INVALID_DSGN_ID.getErrorCode(),
					ErrorCodeEnum.INVALID_DSGN_ID.getErrorMessage() + " : " + dsgnId);
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

		log.info("Employees by role : {} || response : {}", role, responseDto);

		return responseDto;
	}

	@Override
	public List<EmployeeResponseDto> getAllEmployeesByStatus(String status) {
		log.info("Get all employees by employee status: {}", status);

		EmpStatusEnum enumStatus;
		try {
			log.info("Parsing status: {}", status);

			enumStatus = EmpStatusEnum.valueOf(status.toUpperCase());
		} catch (IllegalArgumentException e) {
			log.error("Invalid employee status || getAllEmployeesByStatus : {} ", status);

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

		throw new BadRequestException(ErrorCodeEnum.INVALID_SEARCH.getErrorCode(),
				ErrorCodeEnum.INVALID_SEARCH.getErrorMessage());
	}

	@Override
	public EmployeeResponseDto updateEmployeeById(int empId, EmployeeUpdateDto empDto) {
		log.info("Updating employee with employee id: {} | employee update dto : {} ", empId, empDto);

		Employee employee = employeeRepo.findById(empId).orElseThrow(
				() -> new ResourceNotFoundException(ErrorCodeEnum.RESOURCE_WITH_ID__NOT_FOUND.getErrorCode(),
						ErrorCodeEnum.RESOURCE_WITH_ID__NOT_FOUND.getErrorMessage()
								+ " || Failed to Update employee with ID:  " + empId));

		if (empDto.getDepartmentId() != null && !departmentClient.isValidDepartment(empDto.getDepartmentId())) {
			log.error("Department is not valid : {} ", empDto.getDepartmentId());

			throw new BadRequestException(ErrorCodeEnum.INVALID_DEPT_ID.getErrorCode(),
					ErrorCodeEnum.INVALID_DEPT_ID.getErrorMessage() + " : " + empDto.getDepartmentId());
		}

		if (empDto.getDesignationId() != null && !designationClient.isValidDesignation(empDto.getDesignationId())) {
			log.error("Designation is not valid : {} ", empDto.getDesignationId());

			throw new BadRequestException(ErrorCodeEnum.INVALID_DSGN_ID.getErrorCode(),
					ErrorCodeEnum.INVALID_DSGN_ID.getErrorMessage() + " : " + empDto.getDesignationId());
		}

		modelMapper.map(empDto, employee);

		Employee savedEmployee = employeeRepo.save(employee);

		EmployeeResponseDto response = modelMapper.map(savedEmployee, EmployeeResponseDto.class);

		log.info("Employee updated with id {} | employee :  {} ", empId, response);

		return response;
	}

}
