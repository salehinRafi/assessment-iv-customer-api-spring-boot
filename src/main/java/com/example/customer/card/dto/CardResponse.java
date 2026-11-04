package com.example.customer.card.dto;

import com.example.customer.card.entity.CardStatus;

import java.time.LocalDateTime;

public record CardResponse(
        Long id,
        Long accountId,
        String cardNumber,
        String cardType,
        Integer expiryMonth,
        Integer expiryYear,
        CardStatus status,
        LocalDateTime createAt,
        LocalDateTime updateAt
) {

    @Override
    public String toString(){
        return "CardResponse[id="+id+", status="+status+"]";
    }
}
