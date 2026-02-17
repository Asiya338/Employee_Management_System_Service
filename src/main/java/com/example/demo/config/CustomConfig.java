package com.example.demo.config;

import java.time.Duration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.netty.http.client.HttpClient;

@Configuration
@Slf4j
@EnableCaching
public class CustomConfig {

	@Bean
	ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setSkipNullEnabled(true);

		log.info("Model Mapper Configured with STRICT matching strategy and skip null enabled");

		return mapper;
	}

	@Bean
	public WebClient.Builder webClientBuilder() {
		return WebClient.builder();
	}

	@Bean
	RedisCacheConfiguration cacheConfiguration() {

		return RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10))
				// Set the cache expiration time
				.disableCachingNullValues(); // Disable caching of null values
	}

	@Bean
	public WebClient authWebClient(WebClient.Builder builder, @Value("${auth.service.base-url}") String authBaseUrl) {

		HttpClient httpClient = HttpClient.create().responseTimeout(Duration.ofSeconds(2));

		return builder.baseUrl(authBaseUrl).clientConnector(new ReactorClientHttpConnector(httpClient)).build();
	}
}
