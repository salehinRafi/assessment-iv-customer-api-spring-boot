package com.example.customer.external.service;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class ThirdPartyApiConfig {

	@Bean
	RestClient thirdPartyRestClient() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(Duration.ofSeconds(3));
		factory.setReadTimeout(Duration.ofSeconds(5));
		return RestClient.builder()
				.baseUrl("https://jsonplaceholder.typicode.com")
				.requestFactory(factory)
				.build();
	}
}
