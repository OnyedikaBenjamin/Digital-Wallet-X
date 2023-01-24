package com.africa.semicolon.ewallet.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ConfirmOTPRequest {
    private String oTP;
    private String emailAddress;
}
