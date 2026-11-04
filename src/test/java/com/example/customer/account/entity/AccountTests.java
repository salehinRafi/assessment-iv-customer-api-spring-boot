package com.example.customer.account.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountTests {

    @Test
    void givenNewAccount_whenCreated_thenBalanceIsDefault(){
        Account account = new Account();

        assertThat(account.getBalance()).isEqualByComparingTo(BigDecimal.ZERO);
    }
    
    @Test
    void givenNewAccount_whenSaved_thenTimestampsAreSet(){
        Account account = new Account();
        LocalDateTime before = LocalDateTime.now(ZoneOffset.UTC);
        account.onCreate();
        LocalDateTime after =LocalDateTime.now(ZoneOffset.UTC);

        assertThat(account.getCreatedAt()).isBetween(before,after);
        assertThat(account.getUpdatedAt()).isEqualTo(account.getCreatedAt());
    }
}
