package com.enesincekara.order_service.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void shouldCreateOrderSuccessfully() {
        Order order = new Order("test@mail.com",1000,"eur");
        assertEquals("EUR", order.getCurrency());
        assertEquals(OrderStatus.CREATED, order.getStatus());
    }
    @Test
    void shouldNotAllowNegativeAmount(){
        assertThrows(IllegalArgumentException.class,
                () -> new Order("test@mail.com",-10,"eur"));
    }

    @Test
    void shouldNotCancelPaidOrder(){
        Order order = new Order("test@mail.com",1000,"eur");
        order.changeStatus(OrderStatus.PAID);
        assertThrows(IllegalArgumentException.class, order::cancel);

    }

}
