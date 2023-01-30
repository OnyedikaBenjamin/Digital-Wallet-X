package com.africa.semicolon.ewallet.dtos.request;

import com.africa.semicolon.ewallet.enums.MeansOfID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KYCRequest {
    private MeansOfID meansOfID;
    private String homeAddress;

}
