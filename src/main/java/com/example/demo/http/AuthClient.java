package com.example.demo.http;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import com.example.demo.dto.req.EmployeeCreateReqDTO;
import com.example.demo.dto.res.AuthValidateResponseDTO;
import com.example.demo.dto.res.RegisterResponseDTO;
import com.example.demo.enums.ErrorCodeEnum;
import com.example.demo.exception.AuthServiceUnavailableException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.netty.handler.timeout.ReadTimeoutException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthClient {

	private final WebClient authWebClient;

	public AuthValidateResponseDTO validateTokenRaw(String token) {
		return authWebClient.post().uri("/validate-token").header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.retrieve()
				.onStatus(HttpStatusCode::is4xxClientError,
						resp -> Mono.error(new RuntimeException("Invalid or expired JWT")))
				.onStatus(HttpStatusCode::is5xxServerError,
						resp -> Mono.error(new RuntimeException("Auth service unavailable")))
				.bodyToMono(AuthValidateResponseDTO.class).block();
	}

	@CircuitBreaker(name = "authService", fallbackMethod = "authFallback")
	public AuthValidateResponseDTO validateToken(String token) {

		int maxAttempts = 3;
		int attempt = 0;

		log.info("Validating token: {}", token);

		while (attempt < maxAttempts) {
			try {
				attempt++;

				return validateTokenRaw(token);
			} catch (WebClientRequestException ex) {
				log.error("Token validation failed for token: {}", token, ex);

				if (ex.getCause() instanceof ReadTimeoutException) {

					if (attempt >= maxAttempts) {
						log.error("Max retry attempts reached for token validation: {}", token);

						throw new AuthServiceUnavailableException(ErrorCodeEnum.AUTH_RETRY_EXCEPTION.getErrorCode(),
								ErrorCodeEnum.AUTH_RETRY_EXCEPTION.getErrorMessage());

					}

					sleepBeforeRetry();
					continue;
				}
				throw ex;
			}
		}

		throw new AuthServiceUnavailableException(ErrorCodeEnum.AUTH_TIMEOUT_EXCEPTION.getErrorCode(),
				ErrorCodeEnum.AUTH_TIMEOUT_EXCEPTION.getErrorMessage());

//		Retry is allowed ONLY for idempotent READ calls
	}

	private void sleepBeforeRetry() {
		try {
			Thread.sleep(500); // 500 ms delay
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public AuthValidateResponseDTO authFallback(String token, Throwable ex) {

		log.error("Circuit breaker triggered for auth service", ex);

		throw new AuthServiceUnavailableException(ErrorCodeEnum.AUTH_CIRCUIT_OPEN.getErrorCode(),
				ErrorCodeEnum.AUTH_CIRCUIT_OPEN.getErrorMessage());
	}

	public RegisterResponseDTO registerUser(EmployeeCreateReqDTO employeeDto, String email) {

		return authWebClient.post().uri("/register")
				.bodyValue(Map.of("email", email, "username", employeeDto.getName(), "role", employeeDto.getRole()))
				.retrieve().bodyToMono(RegisterResponseDTO.class).block();
	}

}
