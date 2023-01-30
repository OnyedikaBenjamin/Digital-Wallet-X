package com.africa.semicolon.ewallet.services.cardservices;

import com.africa.semicolon.ewallet.data.models.Card;
import com.africa.semicolon.ewallet.dtos.request.AddCardRequest;
import com.africa.semicolon.ewallet.dtos.request.EditCardRequest;
import com.africa.semicolon.ewallet.dtos.request.VerifyCardRequest;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.text.ParseException;

public interface CardService {

    Card addCard(AddCardRequest addCardRequest) throws ParseException, IOException;
    String deleteCard(Long cardId);
    Object verifyCard(VerifyCardRequest verifyCardRequest) throws IOException;
    void editCard(EditCardRequest editCardRequest);

//    List<Card> viewCards(Long userId);
    Card viewCardById(Long cardId);


}
