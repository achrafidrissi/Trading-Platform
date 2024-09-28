package com.tradingplatform.tradeapp.controller;

import com.tradingplatform.tradeapp.config.JwtProvider;
import com.tradingplatform.tradeapp.model.TwoFactorOTP;
import com.tradingplatform.tradeapp.model.User;
import com.tradingplatform.tradeapp.repository.UserRepository;
import com.tradingplatform.tradeapp.response.AuthResponse;
import com.tradingplatform.tradeapp.response.WatchlistService;
import com.tradingplatform.tradeapp.service.CustomUserDetailsService;
import com.tradingplatform.tradeapp.service.EmailService;
import com.tradingplatform.tradeapp.service.TwoFactorOtpService;
import com.tradingplatform.tradeapp.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private TwoFactorOtpService twoFactorOtpService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private WatchlistService watchlistService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register (@RequestBody User user) throws Exception {

        User isEmailExist = userRepository.findByEmail(user.getEmail());

        if(isEmailExist != null){
            throw new Exception("email is already used with another account");
        }
        User newUser = new User();
        newUser.setFullName(user.getFullName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());

        User savedUser = userRepository.save(newUser);

        watchlistService.createWatchlist(savedUser);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);

        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("register success");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) throws Exception {

        String username = user.getEmail();
        String password = user.getPassword();

        Authentication auth = authenticate(username, password);
        
        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);

        User authUser = userRepository.findByEmail(username);

        //We proceed to this step and security layer after checking pwd and username are correct (autenticate method)
        if(user.getTwoFactorAuth().isEnabled()){
            AuthResponse response = new AuthResponse();
            response.setMessage("Two-factor auth is enabled");
            //Telling frontend that 2FA is enabled
            response.setTwoFactorAuthEnabled(true);
            String otp = OtpUtils.generateOtp();

            TwoFactorOTP oldTwoFactorOTP = twoFactorOtpService.findByUser(authUser.getId());
            if(oldTwoFactorOTP != null){
                twoFactorOtpService.deleteTwoFactorOtp(oldTwoFactorOTP);
            }
            TwoFactorOTP newTwoFactorOTP = twoFactorOtpService.createTwoFactorOtp(authUser, otp, jwt);

            emailService.sendVerificationOtpEmail(username, otp);

            response.setSession(newTwoFactorOTP.getId());
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }

        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("login success");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        if(userDetails == null){
            throw new BadCredentialsException("invalid username");
        }
        if(!password.equals(userDetails.getPassword())){
                throw new BadCredentialsException("invalid password");
        }
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                password,
                userDetails.getAuthorities()
        );
    }

    @PostMapping("/two-factor/otp/{otp}")
    public ResponseEntity<AuthResponse> verifySigningOtp(
            @PathVariable String otp,
            @RequestParam String id) throws Exception {
        TwoFactorOTP twoFactorOTP = twoFactorOtpService.findById(id);
        if(twoFactorOtpService.verifyTwoFactorOtp(twoFactorOTP, otp)){
            AuthResponse response = new AuthResponse();
            response.setMessage("Two Factor authentication verified");
            response.setTwoFactorAuthEnabled(true);
            response.setJwt(twoFactorOTP.getJwt());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        throw new Exception("invalid otp");
    }
}
