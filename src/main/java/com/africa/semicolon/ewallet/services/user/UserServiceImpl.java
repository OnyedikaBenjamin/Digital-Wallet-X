package com.africa.semicolon.ewallet.services.user;

import com.africa.semicolon.ewallet.data.models.VerificationOTP;
import com.africa.semicolon.ewallet.data.models.User;
import com.africa.semicolon.ewallet.data.repositories.UserRepo;

import com.africa.semicolon.ewallet.dtos.request.LoginRequest;
import com.africa.semicolon.ewallet.exceptions.GenericHandlerException;


import com.africa.semicolon.ewallet.services.registration.otp.VerificationOTPService;
import com.africa.semicolon.ewallet.utils.OTPGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private VerificationOTPService verificationOTPService;
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
    public String login(LoginRequest loginRequest) {
        User foundUser = userRepo.findUserByEmailAddressIgnoreCase(loginRequest.getEmailAddress())
                .orElseThrow(()-> new GenericHandlerException("User with " + loginRequest.getEmailAddress() + "does not exist "));
        if (foundUser.getIsDisabled())throw new GenericHandlerException("Verify account");
        if (!Objects.equals(foundUser.getPassword(), loginRequest.getPassWord()))throw new GenericHandlerException("Login incorrect");
        return "Login successful";
    }

    @Override
    public Optional<User> findUserByEmailAddress(String emailAddress) {
        return userRepo.findUserByEmailAddressIgnoreCase(emailAddress);
    }

    @Override
    public void enableUser(String emailAddress) {
        userRepo.enableUser(emailAddress);
    }
}
