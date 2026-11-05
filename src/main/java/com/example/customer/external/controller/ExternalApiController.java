package com.example.customer.external.controller;

import com.example.customer.external.dto.ExternalApiResponse;
import com.example.customer.external.service.ExternalApiService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/external/posts")
@RequiredArgsConstructor
public class ExternalApiController {

	private final ExternalApiService service;

	@GetMapping("/{id}")
	public ExternalApiResponse getPost(@PathVariable @Positive Long id) {

		return service.getPost(id);
	}
}
