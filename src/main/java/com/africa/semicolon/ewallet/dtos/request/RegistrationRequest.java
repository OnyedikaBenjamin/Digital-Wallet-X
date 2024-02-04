package com.africa.semicolon.ewallet.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;
}
