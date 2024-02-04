package com.ewallet.ewallet.services.registration.token;

import com.ewallet.ewallet.data.models.ConfirmationOTP;
import com.ewallet.ewallet.data.repositories.ConfirmationOTPRepo;
import com.ewallet.ewallet.exceptions.GenericHandlerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationOTPServiceImpl implements ConfirmationOTPService {
    @Autowired
    private ConfirmationOTPRepo confirmationOTPRepo;

    @Override
    public void saveConfirmationToken(ConfirmationOTP confirmationOTP) {
        confirmationOTPRepo.save(confirmationOTP);
    }

    @Override
    public ConfirmationOTP findByOTP(String oTP) {
        return confirmationOTPRepo.findByOTP(oTP)
                .orElseThrow(()-> new GenericHandlerException("OTP doesnt exist"));
    }
}
