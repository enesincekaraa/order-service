package com.enesincekara.order_service.api;

import com.enesincekara.order_service.api.dto.*;
import com.enesincekara.order_service.domain.Order;
import com.enesincekara.order_service.domain.OrderRepository;

import com.enesincekara.order_service.domain.OrderStatus;
import com.enesincekara.order_service.infrastructure.OrderRepositoryJpa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderRepositoryJpa orderRepositoryJpa;

    public OrderServiceImpl(OrderRepository orderRepository, OrderRepositoryJpa orderRepositoryJpa) {
        this.orderRepository = orderRepository;
        this.orderRepositoryJpa = orderRepositoryJpa;
    }


    @Override
    public OrderResponse createOrder(String idempotencyKey,CreateOrderRequest req) {
        Optional<Order> existing = orderRepository.findByIdempotencyKey(idempotencyKey);

        if (existing.isPresent()) {
            return toResponse(existing.get());
        }
        Order order = new Order(
                req.customerEmail(),
                req.amountCents(),
                req.currency(),
                idempotencyKey
        );
        Order saved = orderRepository.save(order);
        return toResponse(saved);
    }

    @Override
    public OrderDetailResponse getOrderById(UUID id) {
        Order order = findOrder(id);
        return new OrderDetailResponse(
                order.getId(),
                order.getCustomerEmail(),
                order.getAmountCents(),
                order.getCurrency(),
                order.getStatus().name()
        );
    }

    @Override
    public Page<OrderListItemResponse> listOrders(String email, Pageable pageable) {
        return orderRepositoryJpa.findByCustomerEmailContainingIgnoreCase(email, pageable)
                .map(o-> new OrderListItemResponse(
                        o.getId(),
                        o.getCustomerEmail(),
                        o.getAmountCents(),
                        o.getCurrency(),
                        o.getStatus().name()
                ));
    }

    @Override
    public void cancelOrder(UUID orderId) {
        findOrder(orderId).cancel();
    }

    @Override
    public void changeStatus(UUID orderId, ChangeOrderStatusRequest request) {
        OrderStatus status = OrderStatus.valueOf(request.status().toUpperCase());
        findOrder(orderId).changeStatus(status);
    }


    private Order findOrder(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(()-> new IllegalArgumentException("Order not found"));
    }

    private OrderResponse toResponse(Order order) {

        return new OrderResponse(
                order.getId(),
                order.getCustomerEmail(),
                order.getAmountCents(),
                order.getCurrency(),
                order.getIdempotencyKey()
        );
    }
}
