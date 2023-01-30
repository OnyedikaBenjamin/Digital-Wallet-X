package com.africa.semicolon.ewallet.data.repositories;

import com.africa.semicolon.ewallet.data.models.Card;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CardRepo extends JpaRepository<Card, Long>{


}
