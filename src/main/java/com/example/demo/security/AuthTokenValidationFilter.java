package com.example.demo.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.dto.res.AuthValidateResponseDTO;
import com.example.demo.http.AuthClient;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthTokenValidationFilter extends OncePerRequestFilter {

	private final AuthClient authClient;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		String authToken = request.getHeader("Authorization");

		if (authToken == null || !authToken.startsWith("Bearer")) {
			log.error("Invalid or missing auth token");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");

			filterChain.doFilter(request, response);
		}

		String token = authToken.substring(7);

		AuthValidateResponseDTO validationResponse = authClient.validateToken(token);

		if (validationResponse == null && validationResponse.getValid()) {
			log.error("invalid token");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");

			filterChain.doFilter(request, response);
		}

		List<GrantedAuthority> authorities = new ArrayList<>();

		if (validationResponse.getPermissions() != null) {
			validationResponse.getPermissions().forEach(p -> authorities.add(new SimpleGrantedAuthority(p)));
		}

		if (validationResponse.getRole() != null) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + validationResponse.getRole()));
		}

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				validationResponse.getEmail(), null, authorities);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		filterChain.doFilter(request, response);
	}

	private boolean isValidToken(String token) {
		// Implement your token validation logic here
		return true;
	}

}
