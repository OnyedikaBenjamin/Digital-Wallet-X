package com.africa.semicolon.ewallet.services.registration.otp;

import com.africa.semicolon.ewallet.data.models.ConfirmationOTP;
import com.africa.semicolon.ewallet.data.repositories.ConfirmationOTPRepo;
import com.africa.semicolon.ewallet.exceptions.GenericHandlerException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationOTPServiceImpl implements ConfirmationOTPService{
    @Autowired
    ConfirmationOTPRepo confirmationOTPRepo;
    @Override
    public void saveConfirmationOTP(ConfirmationOTP confirmationOTP) {
        confirmationOTPRepo.save(confirmationOTP);
    }

    @Override
    public ConfirmationOTP findByOTP(String otp) {
        return confirmationOTPRepo.findByoTP(otp)
                .orElseThrow(()->new GenericHandlerException("Invalid oTP"));
    }
}
