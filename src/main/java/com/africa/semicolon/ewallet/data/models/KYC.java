package com.africa.semicolon.ewallet.data.models;

import com.africa.semicolon.ewallet.enums.MeansOfID;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
public class KYC {
    @Id
    private Long id;
    private MeansOfID meansOfID;
    @NotEmpty(message = "This field is not required")
    @NotBlank(message = "This field is required")
    private String homeAddress;

}
