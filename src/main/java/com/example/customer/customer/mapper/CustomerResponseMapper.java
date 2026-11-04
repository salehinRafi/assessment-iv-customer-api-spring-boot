package com.example.customer.customer.mapper;

import java.util.List;

import com.example.customer.account.dto.AccountResponse;
import com.example.customer.account.entity.Account;
import com.example.customer.card.dto.CardResponse;
import com.example.customer.card.entity.Card;
import com.example.customer.customer.dto.CustomerResponse;
import com.example.customer.customer.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerResponseMapper {

	public CustomerResponse toCustomerResponse(Customer customer, List<AccountResponse> accounts) {
		return new CustomerResponse(
				customer.getId(), customer.getName(), customer.getEmail(),
				customer.getPhone(), customer.getAddress(), customer.getCountry(),
				customer.getIdentityType(), customer.getIdentityNumber(), accounts,
				customer.getCreatedAt(), customer.getUpdatedAt());
	}

	public AccountResponse toAccountResponse(Account account, List<CardResponse> cards) {
		return new AccountResponse(
				account.getId(), account.getCustomer().getId(), account.getAccountNumber(),
				account.getAccountType(), account.getBalance(), cards,
				account.getCreatedAt(), account.getUpdatedAt());
	}

	public CardResponse toCardResponse(Card card) {
		return new CardResponse(
				card.getId(), card.getAccount().getId(), card.getCardNumber(),
				card.getCardType(), card.getExpiryMonth(), card.getExpiryYear(),
				card.getStatus(), card.getCreatedAt(), card.getUpdatedAt());
	}
}
