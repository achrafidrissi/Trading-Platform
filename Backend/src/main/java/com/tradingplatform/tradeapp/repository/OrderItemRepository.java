package com.tradingplatform.tradeapp.repository;

import com.tradingplatform.tradeapp.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
