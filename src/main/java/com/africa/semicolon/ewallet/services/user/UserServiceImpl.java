package com.africa.semicolon.ewallet.services.user;

import com.africa.semicolon.ewallet.data.models.*;
import com.africa.semicolon.ewallet.data.repositories.UserRepo;

import com.africa.semicolon.ewallet.dtos.request.*;

import com.africa.semicolon.ewallet.dtos.response.accountverificationpaystackresponse.AccountVerificationPaystackResponse;
import com.africa.semicolon.ewallet.dtos.response.bankcoderesponse.Bank;
import com.africa.semicolon.ewallet.dtos.response.bankcoderesponse.BankCodePaystackResponse;
import com.africa.semicolon.ewallet.dtos.response.bvnvalidationpaystackresponse.BVNValidationPaystackResponse;
import com.africa.semicolon.ewallet.dtos.response.createtransferrecipientpaystackresponse.CreateTransferRecipientPaystackResponse;
import com.africa.semicolon.ewallet.dtos.response.getbankspaystackresponse.BankName;
import com.africa.semicolon.ewallet.dtos.response.getbankspaystackresponse.GetBanksPaystackResponse;
import com.africa.semicolon.ewallet.exceptions.GenericHandlerException;



import com.africa.semicolon.ewallet.services.cardservices.CardService;
import com.africa.semicolon.ewallet.services.kycservices.KYCService;
import com.africa.semicolon.ewallet.services.nextofkinservices.NextOfKinService;
import com.africa.semicolon.ewallet.services.registration.otp.VerificationOTPService;
import com.africa.semicolon.ewallet.utils.OTPGenerator;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
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
    @Autowired
    private KYCService kycService;
    @Autowired
    private NextOfKinService nextOfKinService;
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
        return cardService.viewCard(userId);
    }

    @Override
    public String addCard(Long userId, AddCardRequest addCardRequest) throws ParseException, IOException {
        User foundUser = userRepo.findById(userId).get();
        Card newCard = cardService.addCard(addCardRequest);
        foundUser.getCardList().add(newCard);
        saveUser(foundUser);
        return "Card added successfully";
    }

    @Override
    public AccountVerificationPaystackResponse verifyReceiverAccount(AccountVerificationRequest accountVerificationRequest) throws IOException {
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
    public List<BankName> getListOfBanks() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.paystack.co/bank?currency=NGN")
                .get()
                .addHeader("Authorization", "Bearer "+SECRET_KEY)
                .build();

        try (ResponseBody response = client.newCall(request).execute().body()){
            JsonFactory jsonFactory = new JsonFactory();
            ObjectMapper objectMapper = new ObjectMapper(jsonFactory);
            GetBanksPaystackResponse getBanksPaystackResponse
                    = objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .readValue(response.string(), GetBanksPaystackResponse.class);

            return getBanksPaystackResponse.getData();

        }


    }

    @Override
    public BVNValidationPaystackResponse bvnValidation(BvnValidationRequest bvnValidationRequest) throws IOException {

            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");

            JSONObject json = new JSONObject();
            try{
                BankCodeRequest bankCodeRequest = new BankCodeRequest();
                bankCodeRequest.setBank_name(bvnValidationRequest.getBankName());
                json.put("bvn", bvnValidationRequest.getBvn());
                json.put("bank_code", getBankCode(bankCodeRequest));
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
    public String createTransferRecipient(CreateTransferRecipientRequest createTransferRecipientRequest) throws IOException {
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
    public JsonNode initiateTransfer(InitiateTransferRequest initiateTransferRequest) throws IOException {
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

        try (ResponseBody response = client.newCall(request).execute().body()){
            JsonFactory jsonFactory = new JsonFactory();
            ObjectMapper mapper = new ObjectMapper(jsonFactory);
            return mapper.readTree(response.string());
        }
    }

    @Override
    public String UpdateUserInfo(Long userId, UpdateUserInfoRequest updateUserInfoRequest) throws ParseException, IOException {
        User foundUser = userRepo.findById(userId).get();
        Card addedCard = cardService.addCard(updateUserInfoRequest.getAddCardRequest());
        KYC addedKyc = kycService.addKYC(updateUserInfoRequest.getKycRequest());
        NextOfKin addedNextOfKin = nextOfKinService.addNextOfKin(updateUserInfoRequest.getNextOfKinRequest());
        foundUser.getCardList().add(addedCard);
        foundUser.setKyc(addedKyc);
        foundUser.setNextOfKin(addedNextOfKin);
        userRepo.save(foundUser);
        return "user information updated successfully!";
    }
    @Override
    public String deleteUser(Long userId, DeleteUserRequest deleteUserRequest) {
        User foundUser = userRepo.findById(userId).get();
        if (passwordEncoder.matches(deleteUserRequest.getPassword(), foundUser.getPassword())){
            foundUser.setEmailAddress("deleted"+foundUser.getEmailAddress()+UUID.randomUUID());
            cardService.deleteUserCards(userId);
            saveUser(foundUser);
            return "Account deleted successfully";
        }
        else throw new GenericHandlerException("Incorrect Password");
    }

    @Override
    public User findUserById(Long userId) {
        return userRepo.findById(userId).orElseThrow(()-> new GenericHandlerException("user doesnt exist"));
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
