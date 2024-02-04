package com.ewallet.ewallet.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ConfirmTokenRequest {

    private String token;
    private String emailAddress;
}
