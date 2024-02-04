package com.ewallet.ewallet.services.user;

import com.ewallet.ewallet.data.models.User;
import java.util.Optional;

public interface UserService {
    String createAccount(User user);

     Optional<User>findUserByEmailAddress(String emailAddress);
}
