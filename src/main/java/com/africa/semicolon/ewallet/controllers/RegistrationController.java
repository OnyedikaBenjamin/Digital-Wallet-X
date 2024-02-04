package com.africa.semicolon.ewallet.controllers;

import com.africa.semicolon.ewallet.dtos.request.RegistrationRequest;
import com.africa.semicolon.ewallet.services.registration.otp.RegistrationService;
import com.africa.semicolon.ewallet.utils.ApiResponse;
import com.africa.semicolon.ewallet.dtos.request.SendOTPRequest;
import com.africa.semicolon.ewallet.dtos.request.VerifyOTPRequest;
import com.africa.semicolon.ewallet.services.registration.otp.RegistrationService;
import com.africa.semicolon.ewallet.utils.ApiResponse;
import jakarta.mail.MessagingException;
>>>>>>> aee9af0e7b39e430cb08826f8cf583fa2f823c76
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
=======
import org.springframework.web.bind.annotation.*;
>>>>>>> aee9af0e7b39e430cb08826f8cf583fa2f823c76

import java.time.ZonedDateTime;

@RestController
<<<<<<< HEAD
=======
@CrossOrigin(origins = "http://localhost:3000")
>>>>>>> aee9af0e7b39e430cb08826f8cf583fa2f823c76
@RequestMapping("/api/v1/registration")
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest registrationRequest,
<<<<<<< HEAD
                                      HttpServletRequest httpServletRequest){
=======
                                      HttpServletRequest httpServletRequest) throws MessagingException {
>>>>>>> aee9af0e7b39e430cb08826f8cf583fa2f823c76
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
<<<<<<< HEAD
=======


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

>>>>>>> aee9af0e7b39e430cb08826f8cf583fa2f823c76
}
