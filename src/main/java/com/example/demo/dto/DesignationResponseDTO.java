package com.example.demo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DesignationResponseDTO {

	private Long id;

	private String name;

	private String code;

	private String status;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private Boolean valid;

	private LocalDateTime deletedAt;
}
