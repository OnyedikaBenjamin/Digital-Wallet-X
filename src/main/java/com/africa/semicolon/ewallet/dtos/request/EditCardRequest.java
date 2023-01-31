package com.africa.semicolon.ewallet.dtos.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditCardRequest {

    private String cardName;
    private String cardNumber;
    private String expiryDate;
    private String cvv;

}
