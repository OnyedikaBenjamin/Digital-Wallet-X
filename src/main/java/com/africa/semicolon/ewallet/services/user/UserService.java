package com.africa.semicolon.ewallet.services.user;

import com.africa.semicolon.ewallet.data.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserService {
    String createAccount(User user);
    Optional<User>findUserByEmailAddress(String EmailAddress);

    void enableUser(String emailAddress);
    void saveUser(User user);
}
