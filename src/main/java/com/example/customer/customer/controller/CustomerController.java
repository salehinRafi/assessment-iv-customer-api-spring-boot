package com.example.customer.customer.controller;

import com.example.customer.customer.dto.CreateCustomerRequest;
import com.example.customer.customer.dto.CustomerResponse;
import com.example.customer.customer.dto.UpdateCustomerContactRequest;
import com.example.customer.customer.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService service;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CustomerResponse createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
		return service.createCustomer(request);
	}

	@PatchMapping("/{id}/contact")
	public CustomerResponse updateContact(
            @PathVariable @Positive Long id, @Valid @RequestBody UpdateCustomerContactRequest request
	) {
		return service.updateContact(id, request);
	}

	@GetMapping
	public PagedModel<CustomerResponse> getCustomers(
		@PageableDefault(size = 10, sort = "id") Pageable pageable
	) {
		return new PagedModel<>(service.getCustomers(pageable));
	}
}
