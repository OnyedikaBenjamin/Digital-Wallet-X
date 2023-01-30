package com.africa.semicolon.ewallet.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateUserInfoRequest {
    private KYCRequest kycRequest;
    private AddCardRequest addCardRequest;
    private NextOfKinRequest nextOfKinRequest;
}
