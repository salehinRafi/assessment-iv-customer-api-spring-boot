package com.example.customer.customer.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "phone", length = 30)
    private String phone;

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "country", length = 2)
    private String country;

    @Column(name = "id_type", length = 10)
    private String identityType;

    @Column(name = "id_number", nullable = false, length = 20)
    private String identityNumber;

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