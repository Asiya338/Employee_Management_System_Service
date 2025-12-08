package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.Where;

import com.example.demo.enums.EmpStatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "employees")
@Where(clause = "is_deleted = false")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String name;

	@Column(name = "employee_code", unique = true, nullable = false)
	private String employeeCode;

	@Column(unique = true, nullable = false)
	private String email;

	private String role;

	private LocalDate dob;

	@Column(name = "designation_id")
	private Integer designationId;

	@Column(name = "department_id")
	private Integer departmentId;

	@Column(name = "phone_number")
	private String phoneNumber;

	private Double salary;

	@Column(name = "joined_at")
	private LocalDateTime joinedAt;

	@Column(name = "created_at", insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", insertable = false)
	private LocalDateTime updatedAt;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@Enumerated(EnumType.STRING)
	@Column(name = "employee_status")
	private EmpStatusEnum employeeStatus = EmpStatusEnum.ACTIVE;

	@Column(name = "is_deleted", nullable = false)
	private Boolean isDeleted = false;

	@PrePersist
	void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
		joinedAt = LocalDateTime.now();
	}

	@PreUpdate
	void onUpdate() {
		updatedAt = LocalDateTime.now();
	}

	@PreRemove
	public void preventHardDelete() {
		throw new UnsupportedOperationException("Hard delete is not allowed. Use soft delete instead.");
	}
}