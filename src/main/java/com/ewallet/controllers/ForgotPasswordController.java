package com.ewallet.controllers;
import com.ewallet.dtos.request.ResetPasswordRequest;
import com.ewallet.dtos.request.SendOTPRequest;
import com.ewallet.dtos.request.VerifyOTPRequest;
import com.ewallet.services.resetpassword.ResetPasswordService;
import com.ewallet.utils.ApiResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/forgot-password")
public class ForgotPasswordController {
    @Autowired
    ResetPasswordService resetPasswordService;
    @PostMapping()
    public ResponseEntity<?> forgotPassword(@RequestBody SendOTPRequest sendOTPRequest,
                                            HttpServletRequest httpServletRequest) throws MessagingException {
        String response = resetPasswordService.emailOTP(sendOTPRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(response)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOTP(@RequestBody VerifyOTPRequest verifyOTPRequest,
                                       HttpServletRequest httpServletRequest){
        String response = resetPasswordService.verifyOTP(verifyOTPRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(response)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest,
                                            HttpServletRequest httpServletRequest) throws Exception {
        String response = resetPasswordService.resetPassword(resetPasswordRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(response)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
