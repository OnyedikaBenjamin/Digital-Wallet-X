package com.ewallet.services.resetpassword;

import com.ewallet.dtos.request.ResetPasswordRequest;
import com.ewallet.dtos.request.SendOTPRequest;
import com.ewallet.dtos.request.VerifyOTPRequest;
import jakarta.mail.MessagingException;

public interface ResetPasswordService {
    String emailOTP(SendOTPRequest sendOTPRequest) throws MessagingException;
    String verifyOTP(VerifyOTPRequest verifyOTPRequest);

    String resetPassword(ResetPasswordRequest resetPasswordRequest) throws Exception;

}
