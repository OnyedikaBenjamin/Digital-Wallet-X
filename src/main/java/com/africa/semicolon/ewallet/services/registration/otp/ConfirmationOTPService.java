package com.africa.semicolon.ewallet.services.registration.otp;

import com.africa.semicolon.ewallet.data.models.ConfirmationOTP;



public interface ConfirmationOTPService {
    void saveConfirmationOTP(ConfirmationOTP confirmationOTP);
    ConfirmationOTP findByOTP(String otp);
}
