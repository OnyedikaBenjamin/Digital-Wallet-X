package com.africa.semicolon.ewallet.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SendOTPRequest {
    private String emailAddress;
}
