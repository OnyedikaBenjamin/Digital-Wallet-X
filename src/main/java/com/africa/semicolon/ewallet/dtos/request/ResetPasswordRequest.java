package com.africa.semicolon.ewallet.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResetPasswordRequest {
    private String emailAddress;
    private String password;
    private String confirmPassword;
}
