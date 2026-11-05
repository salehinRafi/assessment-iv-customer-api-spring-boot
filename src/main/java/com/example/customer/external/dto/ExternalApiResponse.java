package com.example.customer.external.dto;

public record ExternalApiResponse(
        Long userId,
        Long id,
        String title,
        String body
) {}
