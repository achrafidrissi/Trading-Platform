package com.tradingplatform.tradeapp.controller;

import com.tradingplatform.tradeapp.request.ForgotPasswordTokenRequest;
import com.tradingplatform.tradeapp.domain.VerificationType;
import com.tradingplatform.tradeapp.model.ForgotPasswordToken;
import com.tradingplatform.tradeapp.model.User;
import com.tradingplatform.tradeapp.model.VerificationCode;
import com.tradingplatform.tradeapp.request.ResetPasswordRequest;
import com.tradingplatform.tradeapp.response.ApiResponse;
import com.tradingplatform.tradeapp.response.AuthResponse;
import com.tradingplatform.tradeapp.service.EmailService;
import com.tradingplatform.tradeapp.service.ForgotPasswordService;
import com.tradingplatform.tradeapp.service.UserService;
import com.tradingplatform.tradeapp.service.VerificationCodeService;
import com.tradingplatform.tradeapp.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    private String jwt;


    @GetMapping("/api/users/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/api/users/verification/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerificationOtp(
            @RequestHeader("Authorization") String jwt,
            @PathVariable VerificationType verificationType) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());
        if (verificationCode == null) {
            verificationCode = verificationCodeService.sendVerificationCode(user, verificationType);
        }
        if(verificationType.equals(VerificationType.EMAIL)) {
            emailService.sendVerificationOtpEmail(user.getEmail(), verificationCode.getOtp());
        }


        return new ResponseEntity<String>("Verification otp sent successfully", HttpStatus.OK);
    }

    @PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication(
            @PathVariable String otp,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());

        String sendTo = verificationCode.getVerificationType().equals(VerificationType.EMAIL)?
                verificationCode.getEmail() : verificationCode.getMobile();

        boolean isVerified = verificationCode.getOtp().equals(otp);

        if(isVerified) {
            User updatedUser = userService.enableTwoFactorAuthentication(
                    verificationCode.getVerificationType(),sendTo,user
            );
            verificationCodeService.deleteVerificationCodeById(verificationCode);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }

        throw new Exception("Wrong otp");
    }

    @PostMapping("/auth/users/reset-password/send-otp")
    public ResponseEntity<AuthResponse> sendForgotPasswordOtp(
            @RequestBody ForgotPasswordTokenRequest request
    ) throws Exception {

        User user = userService.findUserByEmail(request.getSendTo());

        ForgotPasswordToken token = forgotPasswordService.findByUser(user.getId());
        if(token == null) {
            String otp = OtpUtils.generateOtp();
            UUID uuid = UUID.randomUUID();
            String id = uuid.toString();
            token=forgotPasswordService.createToken(
                    user,id,
                    otp,
                    request.getVerificationType(),
                    request.getSendTo());
        }

        if(request.getVerificationType().equals(VerificationType.EMAIL)) {
            emailService.sendVerificationOtpEmail(user.getEmail(), token.getOtp());
        }

        AuthResponse response = new AuthResponse();
        response.setSession(token.getId());
        response.setMessage("Password reset otp sent successfully");

        return new ResponseEntity<AuthResponse>(response, HttpStatus.OK);
    }

    @PatchMapping("/auth/users/reset-password/verify-otp")
    public ResponseEntity<ApiResponse> resetPassword(
            @RequestParam String id,
            @RequestBody ResetPasswordRequest request,
            @RequestHeader("Authorization") String jwt) throws Exception {

        ForgotPasswordToken forgotPasswordToken = forgotPasswordService.findById(id);
        boolean isVerified = forgotPasswordToken.getOtp().equals(request.getOtp());
        if(isVerified) {
            userService.updatePassword(forgotPasswordToken.getUser(),request.getPassword());
            ApiResponse response = new ApiResponse();
            response.setMessage("Password update successfully");
            return new ResponseEntity<ApiResponse>(response, HttpStatus.ACCEPTED);
        }
        throw new Exception("Wrong otp");
    }
}
