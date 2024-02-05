package com.ewallet.data.repositories;

import com.ewallet.data.models.Card;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface CardRepo extends JpaRepository<Card, Long>{
    Optional<Card>findCardByCardNumber(String cardNumber);



    @Transactional
    @Modifying
    @Query("DELETE FROM Card card WHERE card.user.id = :userId")
    void deleteUsersCard(@Param("userId") Long userId);
}
