package com.africa.semicolon.ewallet.services.user;

import com.africa.semicolon.ewallet.data.models.VerificationOTP;
import com.africa.semicolon.ewallet.data.models.User;
import com.africa.semicolon.ewallet.data.repositories.UserRepo;
import com.africa.semicolon.ewallet.services.registration.otp.VerificationOTPService;
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
    private VerificationOTPService confirmationOTPService;
    @Override
    public String createAccount(User user) {
        userRepo.save(user);
        String oTP = OTPGenerator.generateOTP().toString();
        VerificationOTP verificationOTP = new VerificationOTP(
                oTP,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10),
                user
        );
        confirmationOTPService.saveVerificationOTP(verificationOTP);
        return oTP;
    }

    @Override
    public Optional<User> findUserByEmailAddress(String emailAddress) {
        return userRepo.findUserByEmailAddressIgnoreCase(emailAddress);
    }

    @Override
    public void enableUser(String emailAddress) {
        userRepo.enableUser(emailAddress);
    }
}
