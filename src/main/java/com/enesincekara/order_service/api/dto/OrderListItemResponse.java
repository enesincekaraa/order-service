package com.enesincekara.order_service.api.dto;

import java.util.UUID;

public record OrderListItemResponse(
        UUID orderId,
        String customerEmail,
        long amountCents,
        String currency,
        String status
) {}
