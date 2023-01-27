package com.africa.semicolon.ewallet.controllers;

import com.africa.semicolon.ewallet.data.models.Card;
import com.africa.semicolon.ewallet.services.cardservices.CardService;
import com.africa.semicolon.ewallet.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
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
    }

