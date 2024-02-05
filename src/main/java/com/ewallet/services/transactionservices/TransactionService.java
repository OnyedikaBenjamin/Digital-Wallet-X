package com.ewallet.services.transactionservices;

import com.ewallet.data.models.Transaction;
import com.ewallet.dtos.request.CreateTransactionRequest;

import java.util.List;

public interface TransactionService {
  Transaction createTransaction( Long userId, CreateTransactionRequest createTransactionRequest);
  List<Transaction> viewTransaction(Long userId);
}
