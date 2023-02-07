package com.africa.semicolon.ewallet.services.user;

import com.africa.semicolon.ewallet.data.models.Card;
import com.africa.semicolon.ewallet.data.models.User;

import com.africa.semicolon.ewallet.dtos.request.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    String createAccount(User user);

    String login(LoginRequest loginRequest);
    Optional<User>findUserByEmailAddress(String EmailAddress);

    String changePassword(ChangePasswordRequest changePasswordRequest);
    void enableUser(String emailAddress);
    void saveUser(User user);

    List<Card> viewCards(Long userId);
    String addCard(Long userId, AddCardRequest addCardRequest) throws ParseException, IOException;
    Object verifyReceiverAccount(AccountVerificationRequest accountVerificationRequest) throws IOException;
    Object getListOfBanks() throws IOException;
    Object bvnValidation(BvnValidationRequest bvnValidationRequest) throws IOException;
    String getBankCode(BankCodeRequest bankCodeRequest) throws IOException;
    Object createTransferRecipient(CreateTransferRecipientRequest createTransferRecipientRequest) throws IOException;
    Object initiateTransfer(InitiateTransferRequest initiateTransferRequest) throws IOException;
    String UpdateUserInfo(Long userId, UpdateUserInfoRequest updateUserInfoRequest) throws ParseException, IOException;

    String deleteUser(Long userId, DeleteUserRequest deleteUserRequest);

    User findUserById(Long userId);

}
