package com.africa.semicolon.ewallet.services.cardservices;

import com.africa.semicolon.ewallet.data.models.Card;
import com.africa.semicolon.ewallet.dtos.request.EditCardRequest;

public interface CardService {

    Card addCard(Card card);
    String deleteCard(Long cardId);
    String verifyCard(String cardNumber);
    void editCard(EditCardRequest editCardRequest);

//    List<Card> viewCards(Long userId);
    Card viewCardById(Long cardId);

}
