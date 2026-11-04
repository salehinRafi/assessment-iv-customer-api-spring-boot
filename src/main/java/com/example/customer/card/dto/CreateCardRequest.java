package com.example.customer.card.dto;

import com.example.customer.card.entity.CardEnums;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateCardRequest(
        @NotBlank
        @Size(max = 50)
        String accountNumber,

        @NotNull
        CardEnums.Type cardType
) {
}
