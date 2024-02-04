package com.africa.semicolon.ewallet.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountVerificationRequest {
    private String bankCode;
    private String accountNumber;
}
