package com.enesincekara.order_service.infrastructure;

import com.enesincekara.order_service.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepositoryJpa extends JpaRepository<Order, UUID> , com.enesincekara.order_service.domain.OrderRepository {

    Page<Order> findByCustomerEmailContainingIgnoreCase(String email, Pageable pageable);

}
