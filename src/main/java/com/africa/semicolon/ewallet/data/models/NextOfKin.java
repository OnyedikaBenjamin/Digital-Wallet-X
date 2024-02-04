package com.africa.semicolon.ewallet.data.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class NextOfKin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    private String fullName;
    @Email(message = "Email must be valid")
    @NotEmpty(message = "This field is required")
    private String email;
    @NotEmpty(message = "This field is required")
    @NotNull(message = "This field is required")
    private String phoneNumber;
    @NotEmpty(message = "This field is required")
    private String relationship;

}