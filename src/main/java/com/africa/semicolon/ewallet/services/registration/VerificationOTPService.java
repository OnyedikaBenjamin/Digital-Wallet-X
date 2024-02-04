package com.africa.semicolon.ewallet.services.registration;

import com.africa.semicolon.ewallet.data.models.VerificationOTP;

import java.util.Optional;


public interface VerificationOTPService {
    void saveVerificationOTP(VerificationOTP verificationOTP);
    Optional<VerificationOTP> findByOTP(String otp);

    void setVerifiedAt(String otp);

    void deleteOtp();
}
