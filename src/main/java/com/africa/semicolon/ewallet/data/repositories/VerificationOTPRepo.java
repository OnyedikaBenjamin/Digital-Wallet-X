package com.africa.semicolon.ewallet.data.repositories;

import com.africa.semicolon.ewallet.data.models.VerificationOTP;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

@Transactional
public interface VerificationOTPRepo extends JpaRepository<VerificationOTP, Long> {

    void deleteVerificationOTPByExpiredAtBefore(LocalDateTime currentTime);
    Optional<VerificationOTP> findVerificationOTPByOneTimePassword(String oneTimePassword);

    @Modifying
    @Query("UPDATE VerificationOTP verificationOTP " +
            "SET verificationOTP.verifiedAt = ?1 " +
            "WHERE verificationOTP.oneTimePassword = ?2")
    void setVerifiedAt(LocalDateTime now, String oneTimePassword);
}
