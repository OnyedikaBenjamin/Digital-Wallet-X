package com.africa.semicolon.ewallet.data.repositories;

import com.africa.semicolon.ewallet.data.models.Card;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface CardRepo extends JpaRepository<Card, Long>{
    Optional<Card>findCardByCardNumber(String cardNumber);

}
