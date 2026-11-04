package com.example.customer.account.entity;

import com.example.customer.customer.entity.Customer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "account_number", nullable = false, unique = true, length = 50)
    private String accountNumber;

    @Column(name = "account_type", nullable = false, length = 20)
    private String accountType;

    @Column(name = "balance", nullable = false, precision = 19, scale = 2)
    private BigDecimal balance = new BigDecimal("0.00");

    @Column(name = "created_at", nullable = false, updatable = false,columnDefinition = "datetime2(6)")
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false, updatable = false, length = 100)
    private String createdBy;

    @Column(name = "updated_at", nullable = false, columnDefinition = "datetime2(6)")
    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", nullable = false, updatable = false, length = 100)
    private String updatedBy;

    @PrePersist
    void onCreate() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now(ZoneOffset.UTC);
    }
}
