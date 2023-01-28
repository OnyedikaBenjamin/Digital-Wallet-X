package com.africa.semicolon.ewallet.dtos.response.bankcoderesponse;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BankCodePaystackResponse {
    private List<Bank> data;
}
