package com.enesincekara.order_service.domain;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository{
    Order save(Order order);
    Optional<Order> findById(UUID id);
    Optional<Order> findByIdempotencyKey(String idempotencyKey);

}

