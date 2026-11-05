package com.example.customer.external.service;

import com.example.customer.common.exception.ExternalServiceException;
import com.example.customer.common.exception.ResourceNotFoundException;
import com.example.customer.external.dto.ExternalApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Service
@RequiredArgsConstructor
public class ExternalApiService {

	private final RestClient _3rdRestClient;

	public ExternalApiResponse getPost(Long id) {
		try {
			ExternalApiResponse post = _3rdRestClient.get()
					.uri("/posts/{id}", id)
					.retrieve()
					.body(ExternalApiResponse.class);
			if (post == null) {
				throw new ExternalServiceException();
			}
			return post;
		} catch (HttpClientErrorException.NotFound ex) {
			throw new ResourceNotFoundException();
		} catch (RestClientException exception) {
			throw new ExternalServiceException();
		}
	}
}
