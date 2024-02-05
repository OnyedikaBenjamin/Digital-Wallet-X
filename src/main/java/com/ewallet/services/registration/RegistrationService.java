package com.ewallet.services.registration;


import com.ewallet.dtos.request.RegistrationRequest;
import com.ewallet.dtos.request.SendOTPRequest;
import com.ewallet.dtos.request.VerifyOTPRequest;
import jakarta.mail.MessagingException;

public interface RegistrationService {
    String register(RegistrationRequest registrationRequest) throws Exception;

    String verifyOTP(VerifyOTPRequest verifyOTPRequest);

    String resendVerificationOTP(SendOTPRequest sendOTPRequest) throws MessagingException;


}
