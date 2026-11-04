package com.example.customer.customer.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.customer.account.dto.AccountResponse;
import com.example.customer.account.entity.Account;
import com.example.customer.account.repository.AccountRepository;
import com.example.customer.card.dto.CardResponse;
import com.example.customer.card.repository.CardRepository;
import com.example.customer.common.exception.ResourceNotFoundException;
import com.example.customer.customer.dto.CustomerResponse;
import com.example.customer.customer.entity.Customer;
import com.example.customer.customer.mapper.CustomerResponseMapper;
import com.example.customer.customer.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

	private final CustomerRepository customerRepository;
	private final AccountRepository accountRepository;
	private final CardRepository cardRepository;
	private final CustomerResponseMapper mapper;

	public CustomerResponse getCustomer(Long id) {
		Customer customer = customerRepository.findById(id)
			.orElseThrow(ResourceNotFoundException::new);
		Map<Long, List<AccountResponse>> accounts = loadAccounts(List.of(customer));
		return mapper.toCustomerResponse(customer, accounts.getOrDefault(id, List.of()));
	}

	public Page<CustomerResponse> getCustomers(Pageable pageable) {
		Page<Customer> customers = customerRepository.findAll(pageable);
		Map<Long, List<AccountResponse>> accounts = loadAccounts(customers.getContent());
		return customers.map(customer ->
			mapper.toCustomerResponse(customer, accounts.getOrDefault(customer.getId(), List.of())));
	}

	private Map<Long, List<AccountResponse>> loadAccounts(List<Customer> customers) {
		if (customers.isEmpty()) {
			return Map.of();
		}

		List<Long> customerIds = customers.stream().map(Customer::getId).toList();
		List<Account> accounts = accountRepository.findByCustomer_IdInOrderByIdAsc(customerIds);
		if (accounts.isEmpty()) {
			return Map.of();
		}

		List<Long> accountIds = accounts.stream().map(Account::getId).toList();
		Map<Long, List<CardResponse>> cardsByAccount = cardRepository
			.findByAccount_IdInOrderByIdAsc(accountIds).stream()
			.collect(Collectors.groupingBy(
				card -> card.getAccount().getId(),
				Collectors.mapping(mapper::toCardResponse, Collectors.toList())));

		return accounts.stream().collect(Collectors.groupingBy(
			account -> account.getCustomer().getId(),
			Collectors.mapping(
				account -> mapper.toAccountResponse(
					account, cardsByAccount.getOrDefault(account.getId(), List.of())),
				Collectors.toList())));
	}
}
