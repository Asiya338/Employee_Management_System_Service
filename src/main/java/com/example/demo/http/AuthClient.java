package com.example.demo.http;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.dto.res.AuthValidateResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthClient {

	private final WebClient authWebClient;
	private final Duration timeout = Duration.ofSeconds(3);

	public AuthValidateResponseDTO validateToken(String token) {
		log.info("Validating token: {}", token);
		try {
			AuthValidateResponseDTO response = authWebClient.post().uri("/validate-token")
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + token).retrieve()
					.onStatus(HttpStatusCode::is4xxClientError,
							resp -> Mono.error(new RuntimeException("Invalid or expired JWT")))
					.onStatus(HttpStatusCode::is5xxServerError,
							resp -> Mono.error(new RuntimeException("Auth service unavailable")))
					.bodyToMono(AuthValidateResponseDTO.class).timeout(timeout).block();
			log.info("Token validation response: {}", response);

			return response;
		} catch (Exception ex) {
			log.error("Token validation failed for token: {}", token, ex);
			throw new RuntimeException("Token validation failed", ex);
		}
	}
}
