package com.ewallet.ewallet.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegistrationRequest {

    private String firstName;
    private String LastName;
    private String emailAddress;
    private String password;

}
