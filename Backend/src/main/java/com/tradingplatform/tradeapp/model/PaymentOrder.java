package com.tradingplatform.tradeapp.model;

import com.tradingplatform.tradeapp.domain.PaymentMethod;
import com.tradingplatform.tradeapp.domain.PaymentOrderStatus;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class PaymentOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long amount;

    private PaymentOrderStatus status;

    private PaymentMethod paymentMethod;

    @ManyToOne
    private User user;

}
