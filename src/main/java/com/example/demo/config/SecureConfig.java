package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.security.AuthTokenValidationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecureConfig {

	private final AuthTokenValidationFilter authTokenValidationFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
//	          .exceptionHandling(ex -> ex
//	               .authenticationEntryPoint(authenticationEntryPoint)
//	               .accessDeniedHandler(accessDeniedHandler))
				.authorizeHttpRequests(
						auth -> auth.requestMatchers("/public/**", "/actuator/**", "/api/v1/employees/**").permitAll()
								.anyRequest().authenticated())
				.addFilterBefore(authTokenValidationFilter, UsernamePasswordAuthenticationFilter.class)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}
}
