package com.africa.semicolon.ewallet.dtos.request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NextOfKinRequest {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String relationship;
}
