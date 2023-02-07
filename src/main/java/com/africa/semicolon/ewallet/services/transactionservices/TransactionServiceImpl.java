package com.africa.semicolon.ewallet.services.transactionservices;

import com.africa.semicolon.ewallet.data.models.Transaction;
import com.africa.semicolon.ewallet.data.models.User;
import com.africa.semicolon.ewallet.data.repositories.TransactionRepo;
import com.africa.semicolon.ewallet.dtos.request.CreateTransactionRequest;
import com.africa.semicolon.ewallet.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private UserService userService;

    @Override
    public Transaction createTransaction(Long userId, CreateTransactionRequest createTransactionRequest) {
        User foundUser = userService.findUserById(userId);
        Transaction transaction = new Transaction();
        transaction.setTransactionAmount(createTransactionRequest.getAmount());
        transaction.setTransactionDescription(createTransactionRequest.getTransactionDescription());
        transaction.setUser(foundUser);
        return transactionRepo.save(transaction);
    }

    @Override
    public List<Transaction> viewTransaction(Long userId) {
        return transactionRepo.findAll().stream().filter(transaction -> Objects.equals(transaction.getUser().getId(), userId)).toList();
    }

}
