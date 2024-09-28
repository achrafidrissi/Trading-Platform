package com.tradingplatform.tradeapp.service;

import com.tradingplatform.tradeapp.model.TwoFactorOTP;
import com.tradingplatform.tradeapp.model.User;

public interface TwoFactorOtpService {

    TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt);
    TwoFactorOTP findByUser(Long userId);
    TwoFactorOTP findById(String id);
    boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOtp, String otp);
    void deleteTwoFactorOtp(TwoFactorOTP twoFactorOtp);
}