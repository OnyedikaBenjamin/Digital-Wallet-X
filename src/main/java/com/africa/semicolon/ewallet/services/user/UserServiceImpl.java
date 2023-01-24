package com.africa.semicolon.ewallet.services.user;

import com.africa.semicolon.ewallet.data.models.ConfirmationOTP;
import com.africa.semicolon.ewallet.data.models.User;
import com.africa.semicolon.ewallet.data.repositories.UserRepo;
import com.africa.semicolon.ewallet.services.registration.otp.ConfirmationOTPService;
import com.africa.semicolon.ewallet.utils.OTPGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ConfirmationOTPService confirmationOTPService;
    @Override
    public String createAccount(User user) {
        userRepo.save(user);
        String oTP = OTPGenerator.generateOTP().toString();
        ConfirmationOTP confirmationOTP = new ConfirmationOTP(
                oTP,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10),
                user
        );
        confirmationOTPService.saveConfirmationOTP(confirmationOTP);
        return oTP;
    }

    @Override
    public Optional<User> findUserByEmailAddress(String emailAddress) {
        return userRepo.findUserByEmailAddressIgnoreCase(emailAddress);
    }
}
