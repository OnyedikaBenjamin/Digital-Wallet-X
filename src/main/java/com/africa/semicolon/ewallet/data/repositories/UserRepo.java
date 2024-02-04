package com.africa.semicolon.ewallet.data.repositories;

import com.africa.semicolon.ewallet.data.models.User;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;
=======
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
>>>>>>> aee9af0e7b39e430cb08826f8cf583fa2f823c76

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findUserByEmailAddressIgnoreCase(String emailAddress);
<<<<<<< HEAD
=======
    @Transactional
    @Modifying
    @Query("UPDATE User user SET user.isDisabled = false WHERE user.emailAddress=?1")
    void enableUser(String emailAddress);
>>>>>>> aee9af0e7b39e430cb08826f8cf583fa2f823c76
}
