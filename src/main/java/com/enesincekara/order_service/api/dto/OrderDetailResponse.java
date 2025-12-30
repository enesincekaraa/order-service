package com.enesincekara.order_service.api.dto;

import java.util.UUID;

public record OrderDetailResponse(
        UUID orderId,
        String customerEmail,
        long amountCents,
        String currency,
        String status
) {}

