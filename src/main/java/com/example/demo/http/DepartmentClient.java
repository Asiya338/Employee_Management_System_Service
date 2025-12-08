package com.example.demo.http;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.constants.Constant;
import com.example.demo.dto.DepartmentResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class DepartmentClient {

	private final WebClient webClient;

	@Cacheable(value = "departmentCache", key = "#deptId")
	public boolean isValidDepartment(int deptId) {

		log.info("Validate department by ID : {} ", deptId);

		try {
			DepartmentResponseDTO response = webClient.get().uri(Constant.DEPT_BASE_URL + "/check/{deptId}", deptId)
					.retrieve().bodyToMono(DepartmentResponseDTO.class).block();

			log.info("Web client || DepartmentResponseDTO : {} ", response);

			return response != null && Boolean.TRUE.equals(response.getValid());

		} catch (Exception ex) {

			log.error("Department validation failed : {} ", deptId);

			return false;
		}
	}

}
