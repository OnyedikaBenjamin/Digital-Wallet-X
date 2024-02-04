package com.ewallet.ewallet.data.repositories;

import com.ewallet.ewallet.data.models.ConfirmationOTP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationOTPRepo extends JpaRepository<ConfirmationOTP, Long> {
    Optional<ConfirmationOTP>findByOTP(String oTP);
}
