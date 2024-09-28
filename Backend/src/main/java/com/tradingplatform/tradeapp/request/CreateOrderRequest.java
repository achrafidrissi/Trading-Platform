package com.tradingplatform.tradeapp.request;

import com.tradingplatform.tradeapp.domain.OrderType;
import lombok.Data;

@Data
public class CreateOrderRequest {
    private String coinId;
    private double quantity;
    private OrderType orderType;
}
