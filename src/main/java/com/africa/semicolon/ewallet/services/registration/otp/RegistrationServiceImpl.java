package com.africa.semicolon.ewallet.services.registration.otp;

import com.africa.semicolon.ewallet.data.models.User;
import com.africa.semicolon.ewallet.data.models.VerificationOTP;
import com.africa.semicolon.ewallet.dtos.request.RegistrationRequest;
import com.africa.semicolon.ewallet.dtos.request.ResendOTPRequest;
import com.africa.semicolon.ewallet.dtos.request.VerifyOTPRequest;
import com.africa.semicolon.ewallet.enums.Role;
import com.africa.semicolon.ewallet.exceptions.GenericHandlerException;
import com.africa.semicolon.ewallet.services.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService{
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationOTPService verificationOTPService;
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
    public String verifyOTP(VerifyOTPRequest verifyOTPRequest) {
        VerificationOTP otp = verificationOTPService.findByOTP(verifyOTPRequest.getOneTimePassword())
                .orElseThrow(()-> new GenericHandlerException("oneTimePassword is invalid"));

        if(otp.getExpiredAt().isBefore(LocalDateTime.now())){
            throw new GenericHandlerException("Token has expired");
        }

        if(otp.getVerifiedAt() != null){
            throw new GenericHandlerException("Token has been used");
        }
        verificationOTPService.setVerifiedAt(otp.getOneTimePassword());
        userService.enableUser(verifyOTPRequest.getEmailAddress());

        return "verified";
    }

    @Override
    public String resendVerificationOTP(ResendOTPRequest resendOTPRequest) {
        return null;
    }


}
