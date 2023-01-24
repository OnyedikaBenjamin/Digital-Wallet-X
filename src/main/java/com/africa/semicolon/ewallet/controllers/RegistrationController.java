package com.africa.semicolon.ewallet.controllers;

import com.africa.semicolon.ewallet.dtos.request.RegistrationRequest;
import com.africa.semicolon.ewallet.services.registration.otp.RegistrationService;
import com.africa.semicolon.ewallet.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest registrationRequest,
                                      HttpServletRequest httpServletRequest){
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
}
