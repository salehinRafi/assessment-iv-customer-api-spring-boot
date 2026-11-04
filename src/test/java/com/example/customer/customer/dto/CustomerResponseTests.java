package com.example.customer.customer.dto;

import com.example.customer.account.dto.AccountResponse;
import com.example.customer.card.dto.CardResponse;
import com.example.customer.card.entity.CardEnums;
import com.example.customer.customer.entity.IdentityType;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.json.JsonMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerResponseTests {

 @Test
 void givenCustomer_whenReturned_thenIncludeAccountsAndCards(){

  CardResponse card = new CardResponse(
          1L,
          1L,
          "CARD-01",
          CardEnums.Type.DEBIT,
          12,
          2030,
          CardEnums.Status.ACTIVE,
          null,null
  );

  AccountResponse account = new AccountResponse(
          1L,
          1L,
          "ACCOUNT-01",
          "SAVINGS",
          new BigDecimal("1234.56"),
          List.of(card),
          null,
          null
  );

  CustomerResponse customer = new CustomerResponse(
    1L,
    "Salehin",
          "salehin@example.com",
          "0123456789",
          "Mantin",
          "MY",
          IdentityType.NRIC,
          "01234567891011",
          List.of(account),
          null,
          null
  );


  JsonMapper mapper = JsonMapper.builder().build();

  var json = mapper.readTree(mapper.writeValueAsString(customer));
  var cardJson = json.path("accounts").path(0).path("cards").path(0);

  assertThat(json.path("name").asString()).isEqualTo("Salehin");
  assertThat(cardJson.path("status").asString()).isEqualTo(CardEnums.Status.ACTIVE.toString());
 }
}
