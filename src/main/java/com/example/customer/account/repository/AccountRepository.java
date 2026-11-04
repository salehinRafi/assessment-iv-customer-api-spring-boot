package com.example.customer.account.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.customer.account.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	List<Account> findByCustomer_IdInOrderByIdAsc(List<Long> customerIds);
}
