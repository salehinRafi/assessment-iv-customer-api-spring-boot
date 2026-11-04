package com.example.customer.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateAccountRequest(
        @NotBlank
        @Size(max=50)
        String accountNumber,

        @NotBlank
        @Size(max=20)
        String accountType
) {

    @Override
    public String toString(){
        return "CreateAccountRequest";
    }
}
