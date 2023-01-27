package com.africa.semicolon.ewallet.controllers;

import com.africa.semicolon.ewallet.data.models.Card;
import com.africa.semicolon.ewallet.dtos.request.AccountVerificationRequest;
import com.africa.semicolon.ewallet.dtos.request.AddCardRequest;
import com.africa.semicolon.ewallet.dtos.request.ChangePasswordRequest;
import com.africa.semicolon.ewallet.dtos.request.LoginRequest;
import com.africa.semicolon.ewallet.services.user.UserService;
import com.africa.semicolon.ewallet.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.List;

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
    @GetMapping("/cards/{userId}")
    public ResponseEntity<?>viewCards(@PathVariable("userId") Long userId,
                                      HttpServletRequest httpServletRequest){
        List<Card> cardList = userService.viewCards(userId);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(cardList)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);

    }
    @PutMapping("/addCard")
    public ResponseEntity<?>addCards(@RequestBody AddCardRequest addCardRequest, HttpServletRequest httpServletRequest) throws ParseException {
        String putCard = userService.addCard(addCardRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(putCard)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }
    @GetMapping("/verify-account")
    public ResponseEntity<?>verifyReceiversAccount(@RequestBody AccountVerificationRequest accountVerificationRequest, HttpServletRequest httpServletRequest) throws IOException {
        Object verificationResponse = userService.verifyReceiverAccount(accountVerificationRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(verificationResponse)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-banks")
    public ResponseEntity<?>getBanks(HttpServletRequest httpServletRequest) throws IOException {
        Object banks = userService.getListOfBanks();
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(banks)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
