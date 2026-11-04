package com.example.customer.customer.service;

import java.time.YearMonth;
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
import com.example.customer.card.entity.Card;
import com.example.customer.card.repository.CardRepository;
import com.example.customer.common.exception.ResourceNotFoundException;
import com.example.customer.common.generator.NumberGenerator;
import com.example.customer.customer.dto.CreateCustomerRequest;
import com.example.customer.customer.dto.CustomerResponse;
import com.example.customer.customer.dto.UpdateCustomerContactRequest;
import com.example.customer.customer.entity.Customer;
import com.example.customer.customer.mapper.CustomerResponseMapper;
import com.example.customer.customer.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private static final String PERSON = "local-api";

	private final CustomerRepository customerRepository;
	private final AccountRepository accountRepository;
	private final CardRepository cardRepository;
	private final CustomerResponseMapper mapper;
	private final NumberGenerator generator;

	@Transactional
	public CustomerResponse createCustomer(CreateCustomerRequest request) {
		
		Customer customer = new Customer();
		customer.setName(request.name());
		customer.setEmail(request.email());
		customer.setPhone(request.phone());
		customer.setAddress(request.address());
		customer.setCountry(request.country());
		customer.setIdentityType(request.identityType());
		customer.setIdentityNumber(request.identityNumber());
		customer.setCreatedBy(PERSON);
		customer.setUpdatedBy(PERSON);
		customer = customerRepository.save(customer);

		Account account = new Account();
		account.setCustomer(customer);
		account.setAccountNumber(generator.generateAccountNumber());
		account.setAccountType(request.accountType());
		account.setCreatedBy(PERSON);
		account.setUpdatedBy(PERSON);
		account = accountRepository.save(account);

		YearMonth expiry = generator.generateCardExpiry();
		Card card = new Card();
		card.setAccount(account);
		card.setCardNumber(generator.generateCardNumber());
		card.setCvv(generator.generateCvv());
		card.setCardType(request.cardType());
		card.setExpiryMonth(expiry.getMonthValue());
		card.setExpiryYear(expiry.getYear());
		card.setCreatedBy(PERSON);
		card.setUpdatedBy(PERSON);
		card = cardRepository.save(card);

		AccountResponse accountResponse = mapper.toAccountResponse(account, List.of(mapper.toCardResponse(card)));
		return mapper.toCustomerResponse(customer, List.of(accountResponse));
	}

	@Transactional
	public CustomerResponse updateContact(Long id, UpdateCustomerContactRequest request) {
		Customer customer = customerRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
		if (request.email() != null) {
			customer.setEmail(request.email());
		}
		if (request.phone() != null) {
			customer.setPhone(request.phone());
		}
		if (request.address() != null) {
			customer.setAddress(request.address());
		}
		customer.setUpdatedBy(PERSON);
		customerRepository.saveAndFlush(customer);
		return getCustomer(id);
	}

	@Transactional
	public CustomerResponse getCustomer(Long id) {
		Customer customer = customerRepository.findById(id)
			.orElseThrow(ResourceNotFoundException::new);
		Map<Long, List<AccountResponse>> accounts = loadAccounts(List.of(customer));
		return mapper.toCustomerResponse(customer, accounts.getOrDefault(id, List.of()));
	}

	@Transactional
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
