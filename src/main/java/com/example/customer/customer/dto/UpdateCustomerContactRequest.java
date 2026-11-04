package com.example.customer.customer.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateCustomerContactRequest(
        @Email
        @Size(max=255)
        @Pattern(regexp = "(?s).*\\S.*", message = "cannot be blank")
        String email,

        @Size(max=30)
        @Pattern(regexp = "(?s).*\\S.*", message = "cannot be blank")
        String phone,

        @Size(max=500)
        @Pattern(regexp = "(?s).*\\S.*", message = "cannot be blank")
        String address
) {
        @JsonIgnore
        @AssertTrue(message = "provide at least one of email, phone or address")
        public boolean isContactProvided() {
                return email != null || phone!=null || address!=null;
        }
}
