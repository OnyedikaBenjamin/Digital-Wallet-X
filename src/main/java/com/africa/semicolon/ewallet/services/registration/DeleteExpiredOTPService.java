package com.africa.semicolon.ewallet.services.registration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.UUID;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class DeleteExpiredOTPService {

    @Autowired
    private VerificationOTPService verificationOTPService;

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredOtp(){
        System.out.println("deleted");
        verificationOTPService.deleteOtp();
    }


}
