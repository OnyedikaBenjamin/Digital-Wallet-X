package com.africa.semicolon.ewallet.services.transactionservices;

import com.africa.semicolon.ewallet.dtos.request.CreateTransactionRequest;

public interface TransactionService {

  String createTransaction(CreateTransactionRequest createTransactionRequest);

}
