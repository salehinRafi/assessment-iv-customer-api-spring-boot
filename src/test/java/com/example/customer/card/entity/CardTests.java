package com.example.customer.card.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

class CardTests {

    @Test
    void givenNewCard_whenCreated_thenStatusIsInactive() {
        Card card = new Card();

        assertThat(card.getStatus()).isEqualTo(CardEnums.Status.INACTIVE);
    }

    @Test
    void givenNewCard_whenSaved_thenTimestampsAreSet(){
        Card card = new Card();
        LocalDateTime before = LocalDateTime.now(ZoneOffset.UTC);
        card.onCreate();
        LocalDateTime after =LocalDateTime.now(ZoneOffset.UTC);

        assertThat(card.getCreatedAt()).isBetween(before,after);
        assertThat(card.getUpdatedAt()).isEqualTo(card.getCreatedAt());
    }

    @Test
    void givenExistingCard_whenUpdated_thenCreationTimeUnchange(){
        Card card = new Card();
        card.onCreate();
        LocalDateTime createdAt = card.getCreatedAt();
        LocalDateTime before = LocalDateTime.now(ZoneOffset.UTC);
        card.onUpdate();
        LocalDateTime after = LocalDateTime.now(ZoneOffset.UTC);

        assertThat(card.getCreatedAt()).isEqualTo(createdAt);
        assertThat(card.getUpdatedAt()).isBetween(before, after);
    }
}
