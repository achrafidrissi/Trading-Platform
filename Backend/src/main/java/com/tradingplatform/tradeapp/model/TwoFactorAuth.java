package com.tradingplatform.tradeapp.model;

import com.tradingplatform.tradeapp.domain.VerificationType;
import lombok.Data;

@Data
public class TwoFactorAuth {
    private boolean isEnabled = false;
    private VerificationType sendTO;

}
