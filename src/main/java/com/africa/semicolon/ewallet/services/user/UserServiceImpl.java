package com.africa.semicolon.ewallet.services.user;

import com.africa.semicolon.ewallet.data.models.Card;
import com.africa.semicolon.ewallet.data.models.VerificationOTP;
import com.africa.semicolon.ewallet.data.models.User;
import com.africa.semicolon.ewallet.data.repositories.UserRepo;

import com.africa.semicolon.ewallet.dtos.request.*;
import com.africa.semicolon.ewallet.dtos.response.accountverificationpaystackresponse.AccountVerificationPaystackResponse;
import com.africa.semicolon.ewallet.dtos.response.bankcoderesponse.Bank;
import com.africa.semicolon.ewallet.dtos.response.bankcoderesponse.BankCodePaystackResponse;
import com.africa.semicolon.ewallet.dtos.response.bvnvalidationpaystackresponse.BVNValidationPaystackResponse;
import com.africa.semicolon.ewallet.dtos.response.createtransferrecipientpaystackresponse.CreateTransferRecipientPaystackResponse;
import com.africa.semicolon.ewallet.enums.CardStatus;
import com.africa.semicolon.ewallet.exceptions.GenericHandlerException;


import com.africa.semicolon.ewallet.services.cardservices.CardService;
import com.africa.semicolon.ewallet.services.registration.otp.VerificationOTPService;
import com.africa.semicolon.ewallet.utils.OTPGenerator;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CardService cardService;
    @Autowired
    private VerificationOTPService verificationOTPService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final String SECRET_KEY = System.getenv("PAYSTACK_SECRET_KEY");
    @Override
    public String createAccount(User user) {
        saveUser(user);
        String oTP = OTPGenerator.generateOTP().toString();
        VerificationOTP verificationOTP = new VerificationOTP(
                oTP,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10),
                user
        );
        verificationOTPService.saveVerificationOTP(verificationOTP);
        return oTP;
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }

    @Override
    public List<Card> viewCards(Long userId) {
        return userRepo.findById(userId).
                get().getCardList().
                stream().
                filter(cards->cards.getCardStatus().equals(CardStatus.ACTIVE)).
                toList();
    }

    @Override
    public String addCard(AddCardRequest addCardRequest) throws ParseException {
        User foundUser = userRepo.findUserByEmailAddressIgnoreCase(addCardRequest.getEmailAddress()).get();
        Card newCard = cardService.addCard(addCardRequest.getCard());
        foundUser.getCardList().add(newCard);
        saveUser(foundUser);
        return "Card added successfully";
    }

    @Override
    public Object verifyReceiverAccount(AccountVerificationRequest accountVerificationRequest) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.paystack.co/bank/resolve?account_number="+accountVerificationRequest.getAccountNumber()
                        +"&bank_code="+accountVerificationRequest.getBankCode())
                .get()
                .addHeader("Authorization", "Bearer "+SECRET_KEY)
                .build();
        try (ResponseBody response = client.newCall(request).execute().body()) {
            if (!client.newCall(request).execute().isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .readValue(response.string(), AccountVerificationPaystackResponse.class);

        }

    }

    @Override
    public Object getListOfBanks() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.paystack.co/bank?currency=NGN")
                .get()
                .addHeader("Authorization", "Bearer "+SECRET_KEY)
                .build();
        try (ResponseBody response = client.newCall(request).execute().body()){
            JsonFactory jsonFactory = new JsonFactory();
            ObjectMapper mapper = new ObjectMapper(jsonFactory);
            return mapper.readTree(response.string());
        }
    }

    @Override
    public Object bvnValidation(BvnValidationRequest bvnValidationRequest) throws IOException {

            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");

            JSONObject json = new JSONObject();
            try{
                json.put("bvn", bvnValidationRequest.getBvn());
                json.put("bank_code", bvnValidationRequest.getBankCode());
                json.put("account_number", bvnValidationRequest.getAccountNumber());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        RequestBody body = RequestBody.create(mediaType, json.toString());

        Request request = new Request.Builder()
                .url("https://api.paystack.co/bvn/match")
                .post(body)
                .addHeader("Authorization", "Bearer "+SECRET_KEY)
                .addHeader("Content-Type", "application/json")
                .build();
        try (ResponseBody response = client.newCall(request).execute().body()){
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .readValue(response.string(), BVNValidationPaystackResponse.class);

        }

    }

    @Override
    public String getBankCode(BankCodeRequest bankCodeRequest) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.paystack.co/bank?currency=NGN")
                .get()
                .addHeader("Authorization", "Bearer "+SECRET_KEY)
                .build();
        try (ResponseBody response = client.newCall(request).execute().body()){
            ObjectMapper mapper = new ObjectMapper();
            BankCodePaystackResponse bankCodePaystackResponse = mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .readValue(response.string(), BankCodePaystackResponse.class);

            for (Bank bank : bankCodePaystackResponse.getData()) {
                if(bank.getName().equals(bankCodeRequest.getBank_name())){
                    return bank.getCode();
                }
            }
        }
        return null;

    }

    @Override
    public Object createTransferRecipient(CreateTransferRecipientRequest createTransferRecipientRequest) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");

        JSONObject json = new JSONObject();
        try {
            json.put("type", "nuban");
            AccountVerificationRequest accountVerificationRequest = new AccountVerificationRequest();
            accountVerificationRequest.setAccountNumber(createTransferRecipientRequest.getAccountNumber());
            BankCodeRequest bankCodeRequest = new BankCodeRequest();
            bankCodeRequest.setBank_name(createTransferRecipientRequest.getBankName());
            accountVerificationRequest.setBankCode(getBankCode(bankCodeRequest));
            json.put("name", verifyReceiverAccount(accountVerificationRequest));
            json.put("account_number", createTransferRecipientRequest.getAccountNumber());
            json.put("bank_code", getBankCode(bankCodeRequest));
            json.put("currency", "NGN");
        } catch (JSONException | IOException e) {
            throw new RuntimeException(e);
        }
        RequestBody body = RequestBody.create(mediaType, json.toString());

        Request request = new Request.Builder()
                .url("https://api.paystack.co/transferrecipient")
                .post(body)
                .addHeader("Authorization", "Bearer " + SECRET_KEY)
                .addHeader("Content-Type", "application/json")
                .build();
        try (ResponseBody response = client.newCall(request).execute().body()) {
            ObjectMapper objectMapper = new ObjectMapper();
            CreateTransferRecipientPaystackResponse createTransferRecipientPaystackResponse = objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .readValue(response.string(), CreateTransferRecipientPaystackResponse.class);
            return createTransferRecipientPaystackResponse.getData().getRecipient_code();

        }

    }

    @Override
    public Object initiateTransfer(InitiateTransferRequest initiateTransferRequest) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        String uuid = UUID.randomUUID().toString();

        JSONObject json = new JSONObject();
        try {
            json.put("source", "balance");
            json.put("amount", initiateTransferRequest.getAmount());
            json.put("reference", uuid);
            json.put("recipient", createTransferRecipient(initiateTransferRequest.getCreateTransferRecipientRequest()));
            json.put("reason", initiateTransferRequest.getReason());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        RequestBody body = RequestBody.create(mediaType, json.toString());

        Request request = new Request.Builder()
                .url("https://api.paystack.co/transfer")
                .post(body)
                .addHeader("Authorization", "Bearer " + SECRET_KEY)
                .addHeader("Content-Type", "application/json")
                .build();
//
        try (ResponseBody response = client.newCall(request).execute().body()){
            JsonFactory jsonFactory = new JsonFactory();
            ObjectMapper mapper = new ObjectMapper(jsonFactory);
            return mapper.readTree(response.string());
        }
    }


    @Override
    public String login(LoginRequest loginRequest) {
        User foundUser = userRepo.findUserByEmailAddressIgnoreCase(loginRequest.getEmailAddress())
                .orElseThrow(()-> new GenericHandlerException("User with " + loginRequest.getEmailAddress() + "does not exist "));
        if (foundUser.getIsDisabled())throw new GenericHandlerException("Verify account");
        if (!passwordEncoder.matches(loginRequest.getPassword(), foundUser.getPassword()))throw new GenericHandlerException("Incorrect Password");
        return "Login successful";
    }

    @Override
    public Optional<User> findUserByEmailAddress(String emailAddress) {
        return userRepo.findUserByEmailAddressIgnoreCase(emailAddress);
    }

    @Override
    public String changePassword(ChangePasswordRequest changePasswordRequest) {
        User findUser = userRepo.findUserByEmailAddressIgnoreCase(changePasswordRequest.getEmailAddress()).get();
        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), findUser.getPassword())) throw new GenericHandlerException("Incorrect Password!");
        if (!Objects.equals(changePasswordRequest.getNewPassword(), changePasswordRequest.getConfirmNewPassword())) throw new GenericHandlerException("Password Not Match!");
        findUser.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        saveUser(findUser);
        return "Password Successfully Changed";
    }

    @Override
    public void enableUser(String emailAddress) {
        userRepo.enableUser(emailAddress);
    }

}
