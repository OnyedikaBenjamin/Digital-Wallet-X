package com.africa.semicolon.ewallet.services.resetpassword;

import com.africa.semicolon.ewallet.dtos.request.ResetPasswordRequest;
import com.africa.semicolon.ewallet.dtos.request.SendOTPRequest;
import com.africa.semicolon.ewallet.dtos.request.VerifyOTPRequest;
import jakarta.mail.MessagingException;

public interface ResetPasswordService {
    String emailOTP(SendOTPRequest sendOTPRequest) throws MessagingException;
    String verifyOTP(VerifyOTPRequest verifyOTPRequest);

    String resetPassword(ResetPasswordRequest resetPasswordRequest);

}
