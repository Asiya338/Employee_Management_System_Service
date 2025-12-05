package com.example.demo.constants;

import java.util.List;

public class Constant {

	private Constant() {
	};

	public static final String dummyEmpCode = "EMP";

	public static final String formatEmpCode = "EMP%05d";

	public static final String traceId = "traceId";

	public static final List<String> ALLOWED_SORT_FIELDS = List.of("id", "name", "email", "designationId",
			"departmentId", "salary", "dob", "createdAt", "updatedAt", "joinedAt");

	public static final List<Integer> ALLOWED_DEPT_ID = List.of(1, 2, 3, 4, 5);

	public static final List<Integer> ALLOWED_DSGN_ID = List.of(1, 2, 3, 4, 5, 6);
}
