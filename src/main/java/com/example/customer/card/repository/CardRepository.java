package com.example.customer.card.repository;

import com.example.customer.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
	List<Card> findByAccount_IdInOrderByIdAsc(List<Long> accountIds);
}
