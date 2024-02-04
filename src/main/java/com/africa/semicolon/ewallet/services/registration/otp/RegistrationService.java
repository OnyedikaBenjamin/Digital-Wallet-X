package com.africa.semicolon.ewallet.services.registration.otp;

import com.africa.semicolon.ewallet.dtos.request.ConfirmOTPRequest;
import com.africa.semicolon.ewallet.dtos.request.RegistrationRequest;
import com.africa.semicolon.ewallet.dtos.request.ResendOTPRequest;

public interface RegistrationService {
    String register(RegistrationRequest registrationRequest);
    String confirmOTP(ConfirmOTPRequest confirmOTPRequest);
    String resendConfirmationOTP(ResendOTPRequest resendOTPRequest);
}
