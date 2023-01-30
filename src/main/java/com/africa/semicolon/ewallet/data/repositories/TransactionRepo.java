package com.africa.semicolon.ewallet.data.repositories;

import com.africa.semicolon.ewallet.data.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
}
