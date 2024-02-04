package com.africa.semicolon.ewallet.dtos.response.getbankspaystackresponse;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetBanksPaystackResponse {
    private List<BankName>data;
}
