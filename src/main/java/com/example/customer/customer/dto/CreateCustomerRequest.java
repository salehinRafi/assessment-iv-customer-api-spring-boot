package com.example.customer.customer.dto;

import com.example.customer.card.entity.CardEnums;
import com.example.customer.customer.entity.IdentityType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateCustomerRequest(
        @NotBlank
        @Size(max = 150)
        String name,

        @NotBlank
		@Email
        @Size(max = 255)
        String email,

        @NotBlank
        @Size(max = 30)
        String phone,

        @NotBlank
        @Size(max = 500)
        String address,

        @NotBlank
        @Pattern(regexp = "[A-Z]{2}", message = "must be a 2 letter uppercase country")
        String country,

        @NotNull
        IdentityType identityType,

		@NotBlank
		@Pattern(regexp = "[A-Za-z0-9-]{1,64}", message = "must contain 1-64 digit or letters")
		String identityNumber,

		@NotBlank
		@Size(max = 20)
		String accountType,

		@NotNull
		CardEnums.Type cardType
) {

	@Override
	public String toString() {
		return "CreateCustomerRequest";
	}
}
