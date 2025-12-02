USE employee_management;

-- =====================================================
-- INSERT DEPARTMENTS
-- =====================================================

INSERT INTO department (id, name, code, status) VALUES
(1, 'Engineering', 'ENG', 1),
(2, 'Human Resources', 'HR', 1),
(3, 'Finance', 'FIN', 1),
(4, 'Operations', 'OPS', 1),
(5, 'IT Support', 'ITS', 1);

-- =====================================================
-- INSERT DESIGNATIONS
-- =====================================================

INSERT INTO designation (id, name, status) VALUES
(1, 'Intern', 1),
(2, 'Junior Developer', 1),
(3, 'Senior Developer', 1),
(4, 'Team Lead', 1),
(5, 'Manager', 1),
(6, 'Director', 1);

-- =====================================================
-- INSERT ROLES (Optional)
-- =====================================================

INSERT INTO role_master (id, name) VALUES
(1, 'ADMIN'),
(2, 'EMPLOYEE'),
(3, 'MANAGER');
