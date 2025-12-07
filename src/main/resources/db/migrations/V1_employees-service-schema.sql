use employee_management;

DROP TABLE IF EXISTS employees;

CREATE TABLE employees (
    id INT NOT NULL AUTO_INCREMENT,
    
    name VARCHAR(50) NOT NULL,

    employee_code VARCHAR(20) NOT NULL UNIQUE,
    
    email VARCHAR(100) NOT NULL UNIQUE,

    role VARCHAR(50),

    dob DATE,

    designation_id INT NOT NULL,

    department_id INT NOT NULL,

    phone_number VARCHAR(15),

    salary DOUBLE,

    joined_at DATETIME,

    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    created_by VARCHAR(50),

    updated_by VARCHAR(50),

    employee_status ENUM('ACTIVE','INACTIVE','TERMINATED') DEFAULT 'ACTIVE',

    PRIMARY KEY (id)
);