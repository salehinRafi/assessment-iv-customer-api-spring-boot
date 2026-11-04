package com.example.customer.customer.dto;

import com.example.customer.account.dto.AccountResponse;
import com.example.customer.customer.entity.IdentityType;

import java.time.LocalDateTime;
import java.util.List;

public record CustomerResponse(
        Long id,
        String name,
        String email,
        String phone,
        String address,
        String country,
        IdentityType identityType,
        String identityNumber,
        List<AccountResponse> accounts,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
    public CustomerResponse {
        accounts = List.copyOf(accounts);
    }

    @Override
    public String toString(){
        return "CustomerResponse[id="+id+"]";
    }

}
