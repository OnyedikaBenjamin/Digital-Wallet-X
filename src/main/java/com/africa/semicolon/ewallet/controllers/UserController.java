package com.africa.semicolon.ewallet.controllers;

import com.africa.semicolon.ewallet.dtos.request.ChangePasswordRequest;
import com.africa.semicolon.ewallet.dtos.request.LoginRequest;
import com.africa.semicolon.ewallet.services.user.UserService;
import com.africa.semicolon.ewallet.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody LoginRequest loginRequest, HttpServletRequest httpServletRequest){

        String loginUser = userService.login(loginRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(loginUser)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);


    }

    @PutMapping("/change-password")
    public ResponseEntity<?>changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, HttpServletRequest httpServletRequest) {
        String changePasswordResponse = userService.changePassword(changePasswordRequest);
        ApiResponse apiResponse =       ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(changePasswordResponse)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }
}
