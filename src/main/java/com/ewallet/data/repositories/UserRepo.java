package com.ewallet.data.repositories;

import com.ewallet.data.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findUserByEmailAddressIgnoreCase(String emailAddress);
    @Transactional
    @Modifying
    @Query("UPDATE User user SET user.isDisabled = false WHERE user.emailAddress=?1")
    void enableUser(String emailAddress);
}
