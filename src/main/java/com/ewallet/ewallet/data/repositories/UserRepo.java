package com.ewallet.ewallet.data.repositories;

import com.ewallet.ewallet.data.models.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User>findUserByEmailAddressIgnoreCase(String emailAddress);
}
