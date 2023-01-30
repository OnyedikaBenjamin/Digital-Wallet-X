package com.africa.semicolon.ewallet.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class CreateTransactionRequest {
    private String receiverAccountNumber;
    private BigDecimal amount;
    private String transactionDescription;
    private String receiverBankName;

}
