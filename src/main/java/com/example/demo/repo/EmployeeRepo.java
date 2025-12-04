package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Employee;
import com.example.demo.enums.EmpStatusEnum;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

	List<Employee> findByDepartmentId(Integer departmentId);

	List<Employee> findByDesignationId(Integer designationId);

	List<Employee> findByRole(String role);

	List<Employee> findByEmployeeStatus(EmpStatusEnum enumStatus);

	Employee findByEmployeeCode(String employeeCode);

	Employee findByEmail(String email);

	List<Employee> findByNameContainingIgnoreCase(String name);
}
