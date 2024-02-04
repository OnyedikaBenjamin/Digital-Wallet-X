package com.africa.semicolon.ewallet.services.user;

<<<<<<< HEAD
import com.africa.semicolon.ewallet.data.models.User;

=======
import com.africa.semicolon.ewallet.data.models.Card;
import com.africa.semicolon.ewallet.data.models.User;

import com.africa.semicolon.ewallet.dtos.request.*;
import com.africa.semicolon.ewallet.dtos.response.accountverificationpaystackresponse.AccountVerificationPaystackResponse;
import com.africa.semicolon.ewallet.dtos.response.bvnvalidationpaystackresponse.BVNValidationPaystackResponse;
import com.africa.semicolon.ewallet.dtos.response.getbankspaystackresponse.BankName;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
>>>>>>> aee9af0e7b39e430cb08826f8cf583fa2f823c76
import java.util.Optional;

public interface UserService {
    String createAccount(User user);
<<<<<<< HEAD
    Optional<User>findUserByEmailAddress(String EmailAddress);
=======

    String login(LoginRequest loginRequest);
    Optional<User>findUserByEmailAddress(String EmailAddress);

    String changePassword(ChangePasswordRequest changePasswordRequest);
    void enableUser(String emailAddress);
    void saveUser(User user);

    List<Card> viewCards(Long userId);
    String addCard(Long userId, AddCardRequest addCardRequest) throws ParseException, IOException;
    AccountVerificationPaystackResponse verifyReceiverAccount(AccountVerificationRequest accountVerificationRequest) throws IOException;
    List<BankName> getListOfBanks() throws IOException;
    BVNValidationPaystackResponse bvnValidation(BvnValidationRequest bvnValidationRequest) throws IOException;
    String getBankCode(BankCodeRequest bankCodeRequest) throws IOException;
    String createTransferRecipient(CreateTransferRecipientRequest createTransferRecipientRequest) throws IOException;
    JsonNode initiateTransfer(InitiateTransferRequest initiateTransferRequest) throws IOException;
    String UpdateUserInfo(Long userId, UpdateUserInfoRequest updateUserInfoRequest) throws ParseException, IOException;

    String deleteUser(Long userId, DeleteUserRequest deleteUserRequest);

    User findUserById(Long userId);

>>>>>>> aee9af0e7b39e430cb08826f8cf583fa2f823c76
}
