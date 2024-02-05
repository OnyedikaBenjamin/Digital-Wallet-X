package com.ewallet.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddCardRequest {
    private String cardName;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
}
