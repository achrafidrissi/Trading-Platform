package com.tradingplatform.tradeapp.controller;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.tradingplatform.tradeapp.domain.PaymentMethod;
import com.tradingplatform.tradeapp.model.PaymentOrder;
import com.tradingplatform.tradeapp.model.User;
import com.tradingplatform.tradeapp.response.PaymentResponse;
import com.tradingplatform.tradeapp.service.PaymentService;
import com.tradingplatform.tradeapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/payment/{paymentMethod}/amount/{amount}")
    public ResponseEntity<PaymentResponse> paymentHandler(
            @PathVariable PaymentMethod paymentMethod,
            @PathVariable Long amount,
            @RequestHeader("Authorization") String jwt) throws Exception,
            RazorpayException,
            StripeException {

        User user = userService.findUserProfileByJwt(jwt);
        PaymentResponse paymentResponse;

        PaymentOrder order = paymentService.createOrder(user, amount, paymentMethod);

        if (paymentMethod.equals(PaymentMethod.RAZORPAY)) {
            paymentResponse = paymentService.createRazorpayPaymentLink(user, amount);
        } else {
            paymentResponse = paymentService.createStripePaymentLink(user, amount, order.getId());
        }

        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }

}
