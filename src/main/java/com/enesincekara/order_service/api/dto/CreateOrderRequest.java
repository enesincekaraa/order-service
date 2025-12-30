package com.enesincekara.order_service.api.dto;

public record CreateOrderRequest(
        String customerEmail,
        long amountCents,
        String currency
) {}
