package com.africa.semicolon.ewallet.services.registration.otp;


import com.africa.semicolon.ewallet.dtos.request.RegistrationRequest;
import com.africa.semicolon.ewallet.dtos.request.ResendOTPRequest;
import com.africa.semicolon.ewallet.dtos.request.VerifyOTPRequest;

public interface RegistrationService {
    String register(RegistrationRequest registrationRequest);

    String verifyOTP(VerifyOTPRequest verifyOTPRequest);

    String resendVerificationOTP(ResendOTPRequest resendOTPRequest);


}
