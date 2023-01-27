package com.africa.semicolon.ewallet.services.cardservices;

import com.africa.semicolon.ewallet.data.models.Card;
import com.africa.semicolon.ewallet.data.repositories.CardRepo;
import com.africa.semicolon.ewallet.dtos.request.EditCardRequest;
import com.africa.semicolon.ewallet.exceptions.GenericHandlerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static com.africa.semicolon.ewallet.enums.CardStatus.*;

@Service
public class CardServicesImpl implements CardService{

    @Autowired
    private CardRepo cardRepo;

    @Override
    public Card addCard(Card card) throws ParseException {
        String cardExpiryDate = card.getExpiryDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yy");
        simpleDateFormat.setLenient(false);
        Date expiry = simpleDateFormat.parse(cardExpiryDate);
        boolean expired = expiry.before(new Date());
        if (expired)throw new GenericHandlerException("Expired card can't be added");
        return cardRepo.save(card);
    }

    @Override
    public String deleteCard(Long cardId) {
        Card card = cardRepo.findById(cardId).get();
        card.setCardStatus(DELETED);
        cardRepo.save(card);
        return "card deleted successfully";
    }

    @Override
    public String verifyCard(String cardNumber) {
        return null;
    }

    @Override
    public void editCard(EditCardRequest editCardRequest) {

    }

//    @Override
//    public List<Card> viewCards(Long userId){
//        return cardRepo.findById(userId).
//                stream().filter(cards->cards.getCard_status().
//                        equals(ACTIVE)).
//                toList();
//    }


    @Override
    public Card viewCardById(Long cardId) {
        return cardRepo.findById(cardId).filter(card -> card.getCardStatus().equals(ACTIVE)).
                orElseThrow(()-> new GenericHandlerException("Card not available"));
    }
}
