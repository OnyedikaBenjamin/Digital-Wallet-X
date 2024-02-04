package com.ewallet.ewallet.services.registration;

import com.ewallet.ewallet.data.models.User;
import com.ewallet.ewallet.dtos.request.ConfirmTokenRequest;
import com.ewallet.ewallet.dtos.request.RegistrationRequest;
import com.ewallet.ewallet.dtos.request.ResendTokenRequest;
import com.ewallet.ewallet.enums.Role;
import com.ewallet.ewallet.exceptions.GenericHandlerException;
import com.ewallet.ewallet.services.user.UserService;
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
        if (isExist)throw new GenericHandlerException("A user with this email already exist");
        return userService.createAccount(new User(
                registrationRequest.getFirstName(),
                registrationRequest.getLastName(),
                registrationRequest.getEmailAddress(),
                passwordEncoder.encode(registrationRequest.getPassword()),
                Role.User
        ));
    }

    @Override
    public String confirmToken(ConfirmTokenRequest confirmTokenRequest) {
        return null;
    }

    @Override
    public String resendConfirmationToken(ResendTokenRequest resendTokenRequest) {
        return null;
    }
}
