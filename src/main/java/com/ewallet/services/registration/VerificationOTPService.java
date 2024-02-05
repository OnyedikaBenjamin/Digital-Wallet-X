package com.ewallet.services.registration;

import com.ewallet.data.models.VerificationOTP;

import java.util.Optional;


public interface VerificationOTPService {
    void saveVerificationOTP(VerificationOTP verificationOTP);
    Optional<VerificationOTP> findByOTP(String otp);

    void setVerifiedAt(String otp);

    void deleteOtp();
}
