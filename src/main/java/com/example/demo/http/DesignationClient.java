package com.example.demo.http;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.constants.Constant;
import com.example.demo.dto.res.DesignationResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class DesignationClient {

	private final WebClient webClient;

	@Cacheable(value = "designationCache", key = "#dsgnId")
	public boolean isValidDesignation(int dsgnId) {
		log.info(" Validating designation : {} ", dsgnId);

		try {

			DesignationResponseDTO response = webClient.get()
					.uri(Constant.DSGN_BASE_URL + "/check/{designationId}", dsgnId).retrieve()
					.bodyToMono(DesignationResponseDTO.class).block();

			return response != null && response.getValid();

		} catch (Exception ex) {

			log.error("Designation validation failed : {} ", dsgnId);

			return false;
		}
	}
}
