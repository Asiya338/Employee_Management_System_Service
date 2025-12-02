-- =====================================================
-- Drop old database and user (if exists)
-- =====================================================

DROP DATABASE IF EXISTS employee_management;
DROP USER IF EXISTS 'ems'@'%';

-- =====================================================
-- Create database
-- =====================================================

CREATE DATABASE employee_management;

-- =====================================================
-- Create user
-- =====================================================

CREATE USER 'ems'@'%' IDENTIFIED BY 'Ems@2025#Strong';
GRANT ALL PRIVILEGES ON *.* TO 'ems'@'%';
FLUSH PRIVILEGES;

-- =====================================================
-- Select DB
-- =====================================================

USE employee_management;

-- =====================================================
-- MASTER TABLE: Department
-- =====================================================

CREATE TABLE `department` (
  `id` INT NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `code` VARCHAR(20) NOT NULL,
  `status` TINYINT DEFAULT 1,
  `creation_date` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- MASTER TABLE: Designation
-- =====================================================

CREATE TABLE `designation` (
  `id` INT NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `status` TINYINT DEFAULT 1,
  `creation_date` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- MASTER TABLE: Role
-- (Optional – can be used later with Auth-Service)
-- =====================================================

CREATE TABLE `role_master` (
  `id` INT NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- MAIN TABLE: Employees
-- =====================================================

CREATE TABLE `employees` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `employee_code` VARCHAR(20) NOT NULL UNIQUE,
  `email` VARCHAR(100) NOT NULL UNIQUE,
  `password` VARCHAR(255) NOT NULL,
  `role` VARCHAR(50),
  
  `dob` DATE,
  `designation_id` INT,
  `department_id` INT,
  
  `phone_number` BIGINT,
  `salary` DECIMAL(10,2),
  
  `joined_at` TIMESTAMP(2),
  
  `employee_status` ENUM('ACTIVE','INACTIVE','TERMINATED') DEFAULT 'ACTIVE',
  
  `created_at` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2),
  `updated_at` TIMESTAMP(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2),
  
  `created_by` VARCHAR(50),
  `updated_by` VARCHAR(50),

  PRIMARY KEY (`id`),

  -- Foreign Keys
  CONSTRAINT `emp_department_fk` FOREIGN KEY (`department_id`) REFERENCES `department`(`id`),
  CONSTRAINT `emp_designation_fk` FOREIGN KEY (`designation_id`) REFERENCES `designation`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
