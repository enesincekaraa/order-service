package com.enesincekara.order_service.presentation;


import com.enesincekara.order_service.api.dto.CreateOrderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;



    @Test
    void shouldCreateOrder() throws Exception {

        CreateOrderRequest request = new CreateOrderRequest(
                "ci@test.com",
                1500,
                "usd"
        );

        mockMvc.perform(
                        post("/api/orders")
                                .header("Idempotency-Key", "idem-123")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderId").exists())
                .andExpect(jsonPath("$.currency").value("USD"))
                .andExpect(jsonPath("$.idempotencyKey").value("idem-123"));
    }

    @Test
    void shouldReturnSameOrderForSameIdempotencyKey() throws Exception {

        CreateOrderRequest request = new CreateOrderRequest(
                "ci@test.com",
                2000,
                "eur"
        );

        String body = objectMapper.writeValueAsString(request);

        String response1 = mockMvc.perform(
                        post("/api/orders")
                                .header("Idempotency-Key", "idem-999")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body)
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String response2 = mockMvc.perform(
                        post("/api/orders")
                                .header("Idempotency-Key", "idem-999")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body)
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(response1, response2);
    }

    @Test
    void ping_shouldReturnPong() throws Exception {
        mockMvc.perform(get("/api/orders/ping"))
                .andExpect(status().isOk())
                .andExpect(content().string("pong"));
    }




}
