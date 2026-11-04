package com.example.customer.card.repository;

import com.example.customer.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Account, Long> {
}
