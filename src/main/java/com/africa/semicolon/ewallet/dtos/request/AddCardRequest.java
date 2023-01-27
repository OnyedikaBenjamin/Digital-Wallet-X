package com.africa.semicolon.ewallet.dtos.request;

import com.africa.semicolon.ewallet.data.models.Card;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddCardRequest {
    private String emailAddress;
    private Card card;
}
