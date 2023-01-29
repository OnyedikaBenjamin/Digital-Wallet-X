package com.africa.semicolon.ewallet.controllers;

import com.africa.semicolon.ewallet.dtos.request.RegistrationRequest;
import com.africa.semicolon.ewallet.dtos.request.SendOTPRequest;
import com.africa.semicolon.ewallet.dtos.request.VerifyOTPRequest;
import com.africa.semicolon.ewallet.services.registration.otp.RegistrationService;
import com.africa.semicolon.ewallet.utils.ApiResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/registration")
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest registrationRequest,
                                      HttpServletRequest httpServletRequest) throws MessagingException {
        String createUser = registrationService.register(registrationRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(createUser)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }


    @PostMapping("/verify")
    public ResponseEntity<?>verifyOTP(@RequestBody VerifyOTPRequest verifyOTPRequest,
                                      HttpServletRequest httpServletRequest){

        registrationService.verifyOTP(verifyOTPRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .path(httpServletRequest.getRequestURI())
                .data(registrationService.verifyOTP(verifyOTPRequest))
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @PostMapping("/resend-OTP")
    public ResponseEntity<?>resendVerificationOTP(@RequestBody SendOTPRequest sendOTPRequest, HttpServletRequest httpServletRequest) throws MessagingException{
        var oTP = registrationService.resendVerificationOTP(sendOTPRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .data(oTP)
                .statusCode(HttpStatus.OK.value())
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
