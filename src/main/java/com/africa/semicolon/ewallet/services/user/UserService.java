package com.africa.semicolon.ewallet.services.user;

import com.africa.semicolon.ewallet.data.models.User;

import java.util.Optional;

public interface UserService {
    String createAccount(User user);
    Optional<User>findUserByEmailAddress(String EmailAddress);
}
