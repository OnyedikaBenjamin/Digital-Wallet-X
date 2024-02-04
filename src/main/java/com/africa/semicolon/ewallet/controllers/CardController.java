package com.africa.semicolon.ewallet.controllers;

import com.africa.semicolon.ewallet.data.models.Card;
import com.africa.semicolon.ewallet.dtos.request.AddCardRequest;
import com.africa.semicolon.ewallet.dtos.request.EditCardRequest;
import com.africa.semicolon.ewallet.dtos.request.VerifyCardRequest;
import com.africa.semicolon.ewallet.services.cardservices.CardService;
import com.africa.semicolon.ewallet.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.time.ZonedDateTime;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "api/v1/card")
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping("/{cardId}")
    public ResponseEntity<?>viewCard(@PathVariable("cardId")Long cardId, HttpServletRequest httpServletRequest){
        Card getCard = cardService.viewCardById(cardId);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(getCard)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);

    }

    @DeleteMapping("/deleteCard/{cardId}")
    public ResponseEntity<?>deleteCard(@PathVariable("cardId")Long cardId, HttpServletRequest httpServletRequest){
        String removeCard = cardService.deleteCard(cardId);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(removeCard)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }


    @GetMapping("/verify-card")
    public ResponseEntity<?>verifyCard(@RequestBody VerifyCardRequest verifyCardRequest,
                                       HttpServletRequest httpServletRequest) throws IOException {
        Object verifyCard = cardService.verifyCard(verifyCardRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data(verifyCard)
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }



    @PutMapping("/editCard/{cardId}")
    public ResponseEntity<?> editCard(@PathVariable("cardId") Long cardId, @RequestBody EditCardRequest editCardRequest,
                                      HttpServletRequest httpServletRequest) throws ParseException, IOException {
        cardService.editCard(cardId, editCardRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .timeStamp(ZonedDateTime.now())
                .data("Card updated successfully")
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }

    }

