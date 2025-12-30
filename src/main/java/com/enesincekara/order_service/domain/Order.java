package com.enesincekara.order_service.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String customerEmail;

    @Column(nullable = false)
    private Long amountCents;

    @Column(nullable = false,length = 3)
    private String currency; // e.g. "TRY", "EUR"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false,updatable = false)
    private Instant createdAt;

    @Column
    private Instant updatedAt;


    protected Order() {}
    public Order(String customerEmail, long amountCents, String currency) {
        validate(customerEmail, amountCents, currency);

        this.customerEmail = customerEmail;
        this.amountCents = amountCents;
        this.currency = currency.toUpperCase();
        this.status = OrderStatus.CREATED;
        this.createdAt = Instant.now();
    }


    private void touch() {
        this.updatedAt = Instant.now();
    }

    public void cancel() {
        if (this.status == OrderStatus.PAID) {
            throw new IllegalArgumentException("Paid order can not be cancelled");
        }
        if (this.status == OrderStatus.CANCELLED) {
            throw new IllegalArgumentException("Order already cancelled");
        }
        this.status = OrderStatus.CANCELLED;
        touch();
    }

    public void changeStatus(OrderStatus newStatus) {
        if (this.status == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Cancelled order status cannot be changed");
        }
        this.status = newStatus;
        touch();
    }

    private void validate(String email, long amount, String currency) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Customer email is required");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        if (currency == null || currency.length() != 3) {
            throw new IllegalArgumentException("Currency must be ISO-4217");
        }
    }



    public UUID getId() {
        return id;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public long getAmountCents() {
        return amountCents;
    }

    public String getCurrency() {
        return currency;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
