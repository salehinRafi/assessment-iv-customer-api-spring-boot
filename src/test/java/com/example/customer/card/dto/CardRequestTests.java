package com.example.customer.card.dto;

import com.example.customer.card.entity.CardEnums;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CardRequestTests {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

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
    void givenCardDetails_whenValid_thenRequestIsAccepted(){
        CreateCardRequest request = new CreateCardRequest(
                "CARD-1",
                CardEnums.Type.DEBIT
        );
        var violations = validator.validate(request);
        assertThat(violations).isEmpty();
    }

    @Test
    void givenNullCardType_whenValidated_theRequestIsRejected(){
        CreateCardRequest request = new CreateCardRequest(
                "CARD-2",
                null
        );
        var violations = validator.validate(request);
        assertThat(violations).extracting(violation ->{

                    String violationString = violation.getPropertyPath().toString();
                    System.out.println(violationString+":"+violation.getMessage());
                    return  violationString;
                })
                .contains("cardType");
    }

}
