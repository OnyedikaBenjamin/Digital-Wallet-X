package com.africa.semicolon.ewallet.services.cardservices;

import com.africa.semicolon.ewallet.data.models.Card;
import com.africa.semicolon.ewallet.data.repositories.CardRepo;
import com.africa.semicolon.ewallet.dtos.request.AddCardRequest;
import com.africa.semicolon.ewallet.dtos.request.EditCardRequest;
import com.africa.semicolon.ewallet.dtos.request.VerifyCardRequest;
import com.africa.semicolon.ewallet.dtos.response.cardverificationpaystackresponse.CardVerificationPaystackResponse;
import com.africa.semicolon.ewallet.exceptions.GenericHandlerException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Service
public class CardServicesImpl implements CardService{

    private final String SECRET_KEY = System.getenv("PAYSTACK_SECRET_KEY");

    @Autowired
    private CardRepo cardRepo;

    @Override
    public Card addCard(AddCardRequest addCardRequest) throws ParseException, IOException {
        VerifyCardRequest verifyCardRequest = new VerifyCardRequest();
        verifyCardRequest.setCardNumber(addCardRequest.getCardNumber());
        verifyCard(verifyCardRequest);
        cardExpiryDateVerification(addCardRequest.getExpiryDate());
        if (cardRepo.findCardByCardNumber(addCardRequest.getCardNumber()).isPresent())throw new GenericHandlerException("Card already exist");
        Card card = new Card();
        card.setCardName(addCardRequest.getCardName());
        card.setCardNumber(addCardRequest.getCardNumber());
        card.setExpiryDate(addCardRequest.getExpiryDate());
        card.setCvv(addCardRequest.getCvv());
        return cardRepo.save(card);
    }

    private void cardExpiryDateVerification(String expiryDate) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yy");
        simpleDateFormat.setLenient(false);
        Date expiry = simpleDateFormat.parse(expiryDate);
        boolean expired = expiry.before(new Date());
        if (expired)throw new GenericHandlerException("Expired card can't be added");
    }

    @Override
    public String deleteCard(Long cardId) {
        cardRepo.deleteById(cardId);
        return "card deleted successfully";
    }

    @Override
    public Object verifyCard(VerifyCardRequest verifyCardRequest) throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.paystack.co/decision/bin/"
                +verifyCardRequest.getCardNumber().substring(0, 6))
                .get()
                .addHeader("Authorization", "Bearer "+SECRET_KEY)
                .build();

        try (ResponseBody response = client.newCall(request).execute().body()){
            ObjectMapper objectMapper = new ObjectMapper();
            CardVerificationPaystackResponse cardVerificationPaystackResponse
                    = objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .readValue(response.string(), CardVerificationPaystackResponse.class);
            if (cardVerificationPaystackResponse.getData().getLinked_bank_id() == null)throw new GenericHandlerException("Invalid card");
            return cardVerificationPaystackResponse.getData().getLinked_bank_id();

        }

    }

    @Override
    public void editCard(Long cardId, EditCardRequest editCardRequest) {
            Card foundCard = cardRepo.findById(cardId).orElseThrow(() -> new GenericHandlerException("Card with id does not exist"));
            foundCard.setCardName(editCardRequest.getCardName());
            foundCard.setCardNumber(editCardRequest.getCardNumber());
            foundCard.setExpiryDate(editCardRequest.getExpiryDate());
            foundCard.setCvv(editCardRequest.getCvv());
            cardRepo.save(foundCard);
        }



    @Override
    public Card viewCardById(Long cardId) {
        return cardRepo.findById(cardId).get();
    }

    @Override
    public void deleteUserCards(Long userid) {
        cardRepo.deleteUsersCard(userid);
    }

    @Override
    public List<Card> viewCard(Long userId) {
        return cardRepo.findAll().
                stream().
                filter(card -> Objects.equals(card.getUser().getId(), userId)).
                toList();

    }
}
