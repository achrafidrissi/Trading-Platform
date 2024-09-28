package com.tradingplatform.tradeapp.service;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.tradingplatform.tradeapp.domain.PaymentMethod;
import com.tradingplatform.tradeapp.model.PaymentOrder;
import com.tradingplatform.tradeapp.model.User;
import com.tradingplatform.tradeapp.response.PaymentResponse;

public interface PaymentService {

    PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod);

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException;

    PaymentResponse createRazorpayPaymentLink(User user, Long amount) throws RazorpayException;

    PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId) throws StripeException;

}
