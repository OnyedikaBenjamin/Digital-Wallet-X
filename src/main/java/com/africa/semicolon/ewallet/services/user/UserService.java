package com.africa.semicolon.ewallet.services.user;

import com.africa.semicolon.ewallet.data.models.User;
import com.africa.semicolon.ewallet.dtos.request.LoginRequest;

import java.util.Optional;

public interface UserService {
    String createAccount(User user);

    String login(LoginRequest loginRequest);
    Optional<User>findUserByEmailAddress(String EmailAddress);
}
