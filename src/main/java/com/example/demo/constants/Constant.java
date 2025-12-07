package com.example.demo.constants;

import java.util.List;

public class Constant {

	private Constant() {
	};

	public static final String dummyEmpCode = "EMP";

	public static final String formatEmpCode = "EMP%05d";

	public static final String traceId = "traceId";

	public static final String DEPT_BASE_URL = "http://localhost:8088/api/v1/departments";

	public static final String DSGN_BASE_URL = "http://localhost:8089/api/v1/designations";

	public static final List<String> ALLOWED_SORT_FIELDS = List.of("id", "name", "email", "designationId",
			"departmentId", "salary", "dob", "createdAt", "updatedAt", "joinedAt");
}
