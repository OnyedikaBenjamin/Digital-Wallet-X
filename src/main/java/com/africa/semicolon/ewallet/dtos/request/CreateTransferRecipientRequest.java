package com.africa.semicolon.ewallet.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateTransferRecipientRequest {
private String bankName;
private String accountNumber;

}
