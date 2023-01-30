package com.africa.semicolon.ewallet.services.transactionservices;

import com.africa.semicolon.ewallet.data.repositories.TransactionRepo;
import com.africa.semicolon.ewallet.dtos.request.CreateTransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepo transactionRepo;

    @Override
    public String createTransaction(CreateTransactionRequest createTransactionRequest) {
        return null;


    }
}
