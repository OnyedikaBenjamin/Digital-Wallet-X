package com.ewallet.ewallet.services.user;

import com.ewallet.ewallet.data.models.ConfirmationOTP;
import com.ewallet.ewallet.data.models.User;
import com.ewallet.ewallet.data.repositories.UserRepo;
import com.ewallet.ewallet.exceptions.GenericHandlerException;
import com.ewallet.ewallet.services.registration.token.ConfirmationOTPService;
import com.ewallet.ewallet.utils.OTPGenerator;
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
        new ConfirmationOTP(
                oTP,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10),
                user
        );
        return oTP;
    }

    @Override
    public Optional<User> findUserByEmailAddress(String emailAddress) {
        return userRepo.findUserByEmailAddressIgnoreCase(emailAddress);
    }
}
