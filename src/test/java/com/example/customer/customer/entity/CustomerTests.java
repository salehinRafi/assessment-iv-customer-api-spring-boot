package com.example.customer.customer.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTests {

    @Test
    void givenNewCustomer_whenSaved_thenTimestampsAreSet(){
        Customer customer = new Customer();
        LocalDateTime before = LocalDateTime.now(ZoneOffset.UTC);
        customer.onCreate();
        LocalDateTime after =LocalDateTime.now(ZoneOffset.UTC);

        assertThat(customer.getCreatedAt()).isBetween(before,after);
        assertThat(customer.getUpdatedAt()).isEqualTo(customer.getCreatedAt());
    }

    @Test
    void givenExistingCustomer_whenUpdated_thenCreationTimeUnchange(){
        Customer customer = new Customer();
        customer.onCreate();
        LocalDateTime createdAt = customer.getCreatedAt();
        LocalDateTime before = LocalDateTime.now(ZoneOffset.UTC);
        customer.onUpdate();
        LocalDateTime after = LocalDateTime.now(ZoneOffset.UTC);

        assertThat(customer.getCreatedAt()).isEqualTo(createdAt);
        assertThat(customer.getUpdatedAt()).isBetween(before, after);
    }
}
