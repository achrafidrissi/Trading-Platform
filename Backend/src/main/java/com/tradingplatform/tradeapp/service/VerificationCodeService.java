package com.tradingplatform.tradeapp.service;

import com.tradingplatform.tradeapp.domain.VerificationType;
import com.tradingplatform.tradeapp.model.User;
import com.tradingplatform.tradeapp.model.VerificationCode;

public interface VerificationCodeService {
    VerificationCode sendVerificationCode(User user, VerificationType verificationType);

    VerificationCode getVerificationCodeById(Long id) throws Exception;

    VerificationCode getVerificationCodeByUser(Long userId);

    void deleteVerificationCodeById(VerificationCode verificationCode);

}
