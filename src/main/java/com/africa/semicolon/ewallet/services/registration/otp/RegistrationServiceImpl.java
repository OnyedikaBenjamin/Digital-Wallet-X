package com.africa.semicolon.ewallet.services.registration.otp;

import com.africa.semicolon.ewallet.data.models.User;
import com.africa.semicolon.ewallet.dtos.request.ConfirmOTPRequest;
import com.africa.semicolon.ewallet.dtos.request.RegistrationRequest;
import com.africa.semicolon.ewallet.dtos.request.ResendOTPRequest;
import com.africa.semicolon.ewallet.enums.Role;
import com.africa.semicolon.ewallet.exceptions.GenericHandlerException;
import com.africa.semicolon.ewallet.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService{
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public String register(RegistrationRequest registrationRequest) {
        boolean isExist = userService.findUserByEmailAddress(registrationRequest.getEmailAddress())
                .isPresent();
        if (isExist)throw new GenericHandlerException("User with email already exist");
        return userService.createAccount(new User(
                registrationRequest.getFirstName(),
                registrationRequest.getLastName(),
                registrationRequest.getEmailAddress(),
                passwordEncoder.encode(registrationRequest.getPassword()),
                Role.USER
        ));

    }

    @Override
    public String confirmOTP(ConfirmOTPRequest confirmOTPRequest) {
        return null;
    }

    @Override
    public String resendConfirmationOTP(ResendOTPRequest resendOTPRequest) {
        return null;
    }
}
