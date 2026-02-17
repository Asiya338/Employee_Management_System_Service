package com.example.demo.service.impl.helper;

import java.util.NoSuchElementException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Employee;
import com.example.demo.repo.EmployeeRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceHelper {
	private final EmployeeRepo employeeRepo;

	public String[] splitName(String fullName) {
		String[] parts = fullName.trim().split("\\s+");

		String first = parts[0];
		String last = parts.length > 1 ? parts[parts.length - 1] : "";

		return new String[] { first, last };
	}

	public String extractShortCode(String employeeCode) {
		String numeric = employeeCode.replaceAll("\\D+", ""); // EMP00123 → 00123
		return String.valueOf(Integer.parseInt(numeric)); // → 123
	}

	public String generateCompanyEmail(String fullName, String employeeCode) {

		String[] parts = splitName(fullName);
		String first = parts[0].toLowerCase();
		String last = parts[1].toLowerCase();

		String shortCode = extractShortCode(employeeCode);
		String domain = "@company.com";

		String baseEmail;

		if (last.isEmpty()) {
			baseEmail = first + "." + shortCode;
		} else {
			baseEmail = first + "." + last;
		}

		String email = baseEmail + domain;

		int counter = 1;
		if (employeeRepo.existsByEmail(email)) {
			email = baseEmail + counter + domain;
			counter++;
		}

		return email;
	}

	public void checkSelfAccess(Employee employee) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

		if (!isAdmin) {
			String loggedInEmail = auth.getName();

			Employee emp = employeeRepo.findById(employee.getId())
					.orElseThrow(() -> new NoSuchElementException("Employee not found"));

			if (!emp.getEmail().equals(loggedInEmail)) {
				throw new AccessDeniedException("You can access only your own profile");
			}
		}

	}

}
