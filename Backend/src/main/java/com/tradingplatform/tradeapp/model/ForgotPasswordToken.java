package com.tradingplatform.tradeapp.model;

import com.tradingplatform.tradeapp.domain.VerificationType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ForgotPasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @OneToOne
    private User user;

    private String otp;

    //Mobile OR Email
    private VerificationType verificationType;

    private String sendTo;
}
