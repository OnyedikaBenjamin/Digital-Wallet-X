package com.africa.semicolon.ewallet.services.cardservices;

import com.africa.semicolon.ewallet.data.models.Card;
import com.africa.semicolon.ewallet.data.repositories.CardRepo;
import com.africa.semicolon.ewallet.dtos.request.EditCardRequest;
import com.africa.semicolon.ewallet.enums.CARD_STATUS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServicesImpl implements CardService{

    @Autowired
    private CardRepo cardRepo;

    @Override
    public Card addCard(Card card) {
        return cardRepo.save(card);
    }

    @Override
    public void deleteCard(Card card) {
        card.setCard_status(CARD_STATUS.DELETED);
        cardRepo.save(card);
    }

    @Override
    public String verifyCard(String cardNumber) {
        return null;
    }

    @Override
    public void editCard(EditCardRequest editCardRequest) {

    }
}
