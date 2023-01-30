package com.africa.semicolon.ewallet.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BvnValidationRequest {
    private String accountNumber;
    private String bvn;
    private String bankName;
}
