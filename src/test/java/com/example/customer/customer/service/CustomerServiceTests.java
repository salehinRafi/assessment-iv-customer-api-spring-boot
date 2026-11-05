package com.example.customer.customer.service;


import com.example.customer.account.repository.AccountRepository;
import com.example.customer.card.repository.CardRepository;
import com.example.customer.common.exception.ResourceNotFoundException;
import com.example.customer.common.generator.NumberGenerator;
import com.example.customer.customer.mapper.CustomerResponseMapper;
import com.example.customer.customer.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class CustomerServiceTests {

	private final CustomerRepository customerRepository = mock(CustomerRepository.class);
	private final AccountRepository accountRepository = mock(AccountRepository.class);
	private final CardRepository cardRepository = mock(CardRepository.class);

	private final CustomerService service = new CustomerService(
		customerRepository, accountRepository, cardRepository, new CustomerResponseMapper(), mock(NumberGenerator.class));


	@Test
	void givenMissingCustomer_whenRequest_thenReturnNotFound() {
		when(customerRepository.findById(99L)).thenReturn(Optional.empty());
		assertThatThrownBy(() -> service.getCustomer(99L))
			.isInstanceOf(ResourceNotFoundException.class)
			.hasMessage("Resource not found");

		verifyNoInteractions(accountRepository, cardRepository);
	}

	@Test
	void givenCustomPageable_whenRequested_thenReturnResult() {
		var pageable = PageRequest.of(2, 5, Sort.by("name").descending());
		when(customerRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(), pageable, 0));

		var response = service.getCustomers(pageable);

		assertThat(response.getNumber()).isEqualTo(2);
		assertThat(response.getSize()).isEqualTo(5);
		assertThat(response.getSort()).isEqualTo(pageable.getSort());

		verify(customerRepository).findAll(pageable);
		verifyNoInteractions(accountRepository, cardRepository);
	}
}
