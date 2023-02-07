package com.africa.semicolon.ewallet.controllers;

import com.africa.semicolon.ewallet.data.models.Card;
import com.africa.semicolon.ewallet.dtos.request.*;
import com.africa.semicolon.ewallet.dtos.response.accountverificationpaystackresponse.AccountVerificationPaystackResponse;
import com.africa.semicolon.ewallet.dtos.response.bankcoderesponse.Bank;
import com.africa.semicolon.ewallet.dtos.response.bvnvalidationpaystackresponse.BVNValidationPaystackResponse;
import com.africa.semicolon.ewallet.services.user.UserService;
import com.africa.semicolon.ewallet.utils.ApiResponse;
import com.fasterxml.jackson.databind.JsonNode;
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
@CrossOrigin(origins = "http://localhost:3000")
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
        ApiResponse apiResponse = ApiResponse.builder()
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
    @PutMapping("/addCard/{userId}")
    public ResponseEntity<?>addCards(@PathVariable("userId") Long userId, @RequestBody AddCardRequest addCardRequest, HttpServletRequest httpServletRequest) throws ParseException, IOException {
        String putCard = userService.addCard(userId, addCardRequest);
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
        AccountVerificationPaystackResponse verificationResponse = userService.verifyReceiverAccount(accountVerificationRequest);
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
        var banks = userService.getListOfBanks();
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(banks)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PostMapping("/bvn-validation")
    public ResponseEntity<?>bvnValidation(@RequestBody BvnValidationRequest bvnValidationRequest, HttpServletRequest httpServletRequest) throws IOException {
        BVNValidationPaystackResponse response = userService.bvnValidation(bvnValidationRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(response)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-code")
    public ResponseEntity<?>getBankCode(@RequestBody BankCodeRequest bankCodeRequest,
                                        HttpServletRequest httpServletRequest) throws IOException {
        String bankCode = userService.getBankCode(bankCodeRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(bankCode)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PostMapping("/transfer-recipient")
    public ResponseEntity<?>createTransferRecipient(@RequestBody CreateTransferRecipientRequest createTransferRecipientRequest, HttpServletRequest httpServletRequest) throws IOException {
        String response = userService.createTransferRecipient(createTransferRecipientRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(response)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/transfer")
    public ResponseEntity<?>initiateTransfer(@RequestBody InitiateTransferRequest initiateTransferRequest, HttpServletRequest httpServletRequest) throws IOException {
        JsonNode response = userService.initiateTransfer(initiateTransferRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(response)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId")Long userId, @RequestBody DeleteUserRequest deleteUserRequest, HttpServletRequest httpServletRequest){
        Object response = userService.deleteUser(userId,deleteUserRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(response)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

    @PutMapping("/update-user-info/{userId}")
    public ResponseEntity<?>updateUserInformation(@PathVariable("userId") Long userId, @RequestBody  UpdateUserInfoRequest updateUserInfoRequest,
                                                  HttpServletRequest httpServletRequest) throws ParseException, IOException {
        String userInformation = userService.UpdateUserInfo(userId, updateUserInfoRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(userInformation)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }




}
