package com.ewallet.ewallet.services.registration.token;

import com.ewallet.ewallet.data.models.ConfirmationOTP;

public interface ConfirmationOTPService {
    public void saveConfirmationToken(ConfirmationOTP confirmationToken);
    ConfirmationOTP findByOTP(String oTP);
}
