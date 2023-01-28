package com.africa.semicolon.ewallet.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VerifyOTPRequest {

    @NotNull
    private String oneTimePassword;
    private String emailAddress;
}
