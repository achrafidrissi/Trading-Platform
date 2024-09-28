package com.tradingplatform.tradeapp.request;

import com.tradingplatform.tradeapp.domain.VerificationType;
import lombok.Data;

@Data
public class ForgotPasswordTokenRequest {
    private String sendTo;
    private VerificationType verificationType;
}
