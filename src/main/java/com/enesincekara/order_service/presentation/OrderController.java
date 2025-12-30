package com.enesincekara.order_service.presentation;

import com.enesincekara.order_service.api.OrderService;
import com.enesincekara.order_service.api.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/ping")
    public String  ping() {
        return "pong";
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestHeader("Idempotency-Key") String key, @RequestBody CreateOrderRequest request) {
       OrderResponse orderResponse = orderService.createOrder(key,request);
       return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }



    @GetMapping("/{orderId}")
    public OrderDetailResponse getById(@PathVariable UUID orderId) {
        return  orderService.getOrderById(orderId);
    }

    @GetMapping
    public Page<OrderListItemResponse> list(@RequestParam(defaultValue = "")String email, Pageable pageable) {
        return orderService.listOrders(email,pageable);
    }

    @PostMapping("/{orderId}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable UUID orderId) {
        orderService.cancelOrder(orderId);
    }

    @PatchMapping("/{orderId}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeStatus(
            @PathVariable UUID orderId,
            @RequestBody ChangeOrderStatusRequest request) {

        orderService.changeStatus(orderId, request);
    }
    @GetMapping("/internal/health")
    public Map<String, String> health() {
        return Map.of("status", "UP", "service", "order-service");
    }


}
