package com.tradingplatform.tradeapp.service;

import com.tradingplatform.tradeapp.domain.VerificationType;
import com.tradingplatform.tradeapp.model.ForgotPasswordToken;
import com.tradingplatform.tradeapp.model.User;

public interface ForgotPasswordService {
    ForgotPasswordToken createToken(
            User user, String id,
            String otp, VerificationType verificationType, String sendTo
    );

    ForgotPasswordToken findById(String id);

    ForgotPasswordToken findByUser(Long userId);

    void deleteToken(ForgotPasswordToken token);

}
