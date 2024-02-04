package com.ewallet.ewallet.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResendTokenRequest {

    private String emailAddress;
}
