package com.example.customer.account.dto;

import com.example.customer.card.dto.CardResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record AccountResponse (

    Long id,
    Long customerId,
    String accountNumber,
    String accountType,
    BigDecimal balance,
    List<CardResponse> cards,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
    ){

    public AccountResponse {
        cards = List.copyOf(cards);
    }

    @Override
    public String toString() {
        return "AccountResponse[id="+id+", customerId="+customerId+"]";
    }
}
