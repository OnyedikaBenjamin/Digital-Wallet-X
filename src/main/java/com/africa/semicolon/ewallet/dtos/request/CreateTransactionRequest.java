package com.africa.semicolon.ewallet.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Setter
@Getter
public class CreateTransactionRequest {
    private BigDecimal amount;
    private String transactionDescription;
}
