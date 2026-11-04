package com.example.customer.card.dto;

import com.example.customer.card.entity.CardEnums;
import jakarta.validation.constraints.NotNull;

public record UpdateCardStatus(
        @NotNull
        CardEnums.Status status
) {

}
