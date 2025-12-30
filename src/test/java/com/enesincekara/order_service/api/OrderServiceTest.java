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

        String testKey = "idem-key-1";
        CreateOrderRequest request = new CreateOrderRequest(
                "ci@test.com",
                1500,
                "usd"

        );
        OrderResponse response = orderService.createOrder(testKey,request);
        assertNotNull(response.orderId());
        assertEquals("USD", response.currency());
        assertEquals(testKey,response.idempotencyKey());
    }

    @Test
    void shouldReturnSameOrderWhenUsingSameIdempotencyKey() {
        String testKey = "idem-key-2";
        CreateOrderRequest request = new CreateOrderRequest(
                "ci@test.com",
                1500,
                "usd"

        );

        OrderResponse response1 = orderService.createOrder(testKey,request);
        OrderResponse response2 =  orderService.createOrder(testKey,request);
        assertEquals(response1.orderId(),response2.orderId());
        assertEquals(response1.idempotencyKey(),response2.idempotencyKey());
    }
}
