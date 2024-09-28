package com.tradingplatform.tradeapp.service;

import com.tradingplatform.tradeapp.domain.OrderType;
import com.tradingplatform.tradeapp.model.Coin;
import com.tradingplatform.tradeapp.model.Order;
import com.tradingplatform.tradeapp.model.OrderItem;
import com.tradingplatform.tradeapp.model.User;

import java.util.List;

public interface OrderService {
    Order createOrder(User user, OrderItem orderItem, OrderType orderType);

    Order getOrderById(Long orderId) throws Exception;

    List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol);

    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;

}
