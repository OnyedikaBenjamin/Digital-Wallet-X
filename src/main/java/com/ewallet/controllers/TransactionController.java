package com.ewallet.controllers;

import com.ewallet.data.models.Transaction;
import com.ewallet.dtos.request.CreateTransactionRequest;
import com.ewallet.services.transactionservices.TransactionService;
import com.ewallet.utils.ApiResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;
    @PostMapping("/transaction/{userId}")
    public ResponseEntity<?> createTransaction(@PathVariable ("userId") Long userId, @RequestBody CreateTransactionRequest createTransactionRequest,
                                               HttpServletRequest httpServletRequest) throws MessagingException {
        Transaction createTransaction = transactionService.createTransaction(userId, createTransactionRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(createTransaction)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/view-transaction/{userId}")
    public ResponseEntity<?> viewTransaction(@PathVariable("userId")Long userId, HttpServletRequest httpServletRequest){
        List<Transaction> viewTransaction = transactionService.viewTransaction(userId);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(viewTransaction)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}
