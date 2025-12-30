package com.enesincekara.order_service.api;


import com.enesincekara.order_service.api.dto.*;
import com.enesincekara.order_service.domain.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface OrderService {
    OrderResponse createOrder(String idempotencyKey,CreateOrderRequest req);
    OrderDetailResponse getOrderById(UUID id);
    Page<OrderListItemResponse> listOrders(String email, Pageable pageable);
    void cancelOrder(UUID id);
    void changeStatus(UUID orderId, ChangeOrderStatusRequest request);
}
