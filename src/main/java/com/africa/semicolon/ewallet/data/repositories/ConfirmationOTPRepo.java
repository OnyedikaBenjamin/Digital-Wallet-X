package com.africa.semicolon.ewallet.data.repositories;

import com.africa.semicolon.ewallet.data.models.ConfirmationOTP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationOTPRepo extends JpaRepository<ConfirmationOTP, Long> {
    Optional<ConfirmationOTP> findByoTP(String oTP);
}
