package com.tradingplatform.tradeapp.controller;

import com.tradingplatform.tradeapp.domain.OrderType;
import com.tradingplatform.tradeapp.model.Coin;
import com.tradingplatform.tradeapp.model.Order;
import com.tradingplatform.tradeapp.model.User;
import com.tradingplatform.tradeapp.request.CreateOrderRequest;
import com.tradingplatform.tradeapp.service.CoinService;
import com.tradingplatform.tradeapp.service.OrderService;
import com.tradingplatform.tradeapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CoinService coinService;


    @PostMapping("/pay")
    public ResponseEntity<Order> payOrderPayment(
            @RequestHeader("Authorization") String jwt,
            @RequestBody CreateOrderRequest req
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Coin coin = coinService.findById(req.getCoinId());

        Order order = orderService.processOrder(coin, req.getQuantity(),
                req.getOrderType(), user);

        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(
            @RequestHeader("Authorization") String jwtToken,
            @PathVariable Long orderId
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwtToken);

        Order order = orderService.getOrderById(orderId);
        if (order.getUser().getId().equals(user.getId())) {
            return ResponseEntity.ok(order);
        } else {
            throw new Exception("You don't have access to this order");
        }
    }

    @GetMapping()
    public ResponseEntity<List<Order>> getAllOrdersForUser(
            @RequestHeader("Authorization") String jwt,
            @RequestParam(required = false) OrderType order_type,
            @RequestParam(required = false) String asset_symbol
    ) throws Exception {
        Long userId = userService.findUserProfileByJwt(jwt).getId();

        List<Order> userOrders = orderService.getAllOrdersOfUser(
                userId,
                order_type,
                asset_symbol);
        return ResponseEntity.ok(userOrders);
    }



}