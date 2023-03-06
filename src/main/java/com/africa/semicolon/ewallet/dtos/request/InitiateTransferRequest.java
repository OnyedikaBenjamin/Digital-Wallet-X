package com.africa.semicolon.ewallet.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class InitiateTransferRequest {
    private CreateTransferRecipientRequest createTransferRecipientRequest;
    private BigDecimal amount;
    private String reason;
}
