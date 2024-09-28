package com.tradingplatform.tradeapp.utils;

import java.util.Random;

public class OtpUtils {
    public static String generateOtp() {
        int otpLength = 6;
        Random rand = new Random();

        //Create a String of length 6, the reason why we use StringBuilder
        //And not a String varuable because String type is immutable (cannot be changed)
        StringBuilder otp = new StringBuilder(otpLength);

        for (int i = 0; i < otpLength; i++) {
            otp.append(rand.nextInt(10));
        }

        return otp.toString();
    }
}
