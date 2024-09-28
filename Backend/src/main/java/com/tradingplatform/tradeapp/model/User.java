package com.tradingplatform.tradeapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tradingplatform.tradeapp.domain.USER_ROLE;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;
    private String email;

    //expose the password in the response when sending
    // user details back to the client
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Embedded
    private TwoFactorAuth twoFactorAuth = new TwoFactorAuth();

    public USER_ROLE role = USER_ROLE.ROLE_CUSTOMER; //Default role
}
