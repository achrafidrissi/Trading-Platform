package com.tradingplatform.tradeapp.service;

import com.tradingplatform.tradeapp.model.PaymentDetails;
import com.tradingplatform.tradeapp.model.User;

public interface PaymentDetailsService {
    public PaymentDetails addPaymentDetails(String accountNumber,
                                            String accountHolderName,
                                            String ifsc,
                                            String bankName,
                                            User user);

    public PaymentDetails getUsersPaymentDetails(User user);

}
