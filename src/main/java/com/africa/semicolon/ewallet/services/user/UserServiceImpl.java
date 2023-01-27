package com.africa.semicolon.ewallet.services.user;

import com.africa.semicolon.ewallet.data.models.Card;
import com.africa.semicolon.ewallet.data.models.VerificationOTP;
import com.africa.semicolon.ewallet.data.models.User;
import com.africa.semicolon.ewallet.data.repositories.UserRepo;

import com.africa.semicolon.ewallet.dtos.request.AddCardRequest;
import com.africa.semicolon.ewallet.dtos.request.ChangePasswordRequest;
import com.africa.semicolon.ewallet.dtos.request.LoginRequest;
import com.africa.semicolon.ewallet.enums.CardStatus;
import com.africa.semicolon.ewallet.exceptions.GenericHandlerException;


import com.africa.semicolon.ewallet.services.cardservices.CardService;
import com.africa.semicolon.ewallet.services.registration.otp.VerificationOTPService;
import com.africa.semicolon.ewallet.utils.OTPGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CardService cardService;
    @Autowired
    private VerificationOTPService verificationOTPService;
    @Autowired
    private PasswordEncoder passwordEncoder;
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
    public String addCard(AddCardRequest addCardRequest) {
        User foundUser = userRepo.findUserByEmailAddressIgnoreCase(addCardRequest.getEmailAddress()).get();
        Card newCard = cardService.addCard(addCardRequest.getCard());
        foundUser.getCardList().add(newCard);
        saveUser(foundUser);
        return "Card added successfully";
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
