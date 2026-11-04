package com.example.customer.card.dto;

import com.example.customer.card.entity.CardEnums;

import java.time.LocalDateTime;

public record CardResponse(
        Long id,
        Long accountId,
        String cardNumber,
        CardEnums.Type cardType,
        Integer expiryMonth,
        Integer expiryYear,
        CardEnums.Status status,
        LocalDateTime createAt,
        LocalDateTime updateAt
) {

    @Override
    public String toString(){
        return "CardResponse[id="+id+", status="+status+"]";
    }
}
