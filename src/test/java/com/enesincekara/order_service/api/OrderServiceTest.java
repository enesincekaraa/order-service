package com.enesincekara.order_service.api;

import com.enesincekara.order_service.api.dto.CreateOrderRequest;
import com.enesincekara.order_service.api.dto.OrderResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class OrderServiceTest {

    @Autowired
    private OrderService orderService;


    @Test
    void shouldCreateOrder() {
        CreateOrderRequest request = new CreateOrderRequest(
                "ci@test.com",
                1500,
                "usd"
        );
        OrderResponse response = orderService.createOrder(request);
        assertNotNull(response.orderId());
        assertEquals("USD", response.currency());

    }
}
