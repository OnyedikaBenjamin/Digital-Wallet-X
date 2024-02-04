package com.africa.semicolon.ewallet.services.transactionservices;

import com.africa.semicolon.ewallet.data.models.Transaction;
import com.africa.semicolon.ewallet.dtos.request.CreateTransactionRequest;

import java.util.List;

public interface TransactionService {
  Transaction createTransaction( Long userId, CreateTransactionRequest createTransactionRequest);
  List<Transaction> viewTransaction(Long userId);
}
