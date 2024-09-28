package com.tradingplatform.tradeapp.repository;

import com.tradingplatform.tradeapp.model.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {
}
