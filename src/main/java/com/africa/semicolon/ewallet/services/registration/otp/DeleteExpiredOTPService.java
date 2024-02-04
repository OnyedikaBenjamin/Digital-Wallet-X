package com.africa.semicolon.ewallet.services.registration.otp;

import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
>>>>>>> aee9af0e7b39e430cb08826f8cf583fa2f823c76

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class DeleteExpiredOTPService {

<<<<<<< HEAD
=======
    @Autowired
    private VerificationOTPService verificationOTPService;

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredOtp(){
        System.out.println("deleted");
        verificationOTPService.deleteOtp();
    }


>>>>>>> aee9af0e7b39e430cb08826f8cf583fa2f823c76
}
