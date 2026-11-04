package com.example.customer.customer.dto;

import com.example.customer.customer.entity.IdentityType;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerRequestTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    private CreateCustomerRequest customerRequest(){
        return new CreateCustomerRequest (
                "Salehin Rafi",
                "salehin.rafi@example.com",
                "0123456789",
                "Mantin",
                "MY",
                IdentityType.NRIC,
                "01234567891011"
        );
    }


    @BeforeAll
    static void setValidator(){
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void closeValidator(){
        validatorFactory.close();
    }

    @Test
    void givenCustomerDetails_whenValid_thenRequestIsAccepted(){
        CreateCustomerRequest request = customerRequest();

        var violations = validator.validate(request);

        assertThat(violations).isEmpty();
    }

    @Test
    void givenNoContactDetails_whenUpdatingContact_thennRequestIsRejected(){
        UpdateCustomerContactRequest request = new UpdateCustomerContactRequest(
                null,
                null,
                null
        );

        var violations = validator.validate(request);

        assertThat(violations).extracting(
                violation -> violation.getPropertyPath().toString())
                .contains("contactProvided");
    }

}
