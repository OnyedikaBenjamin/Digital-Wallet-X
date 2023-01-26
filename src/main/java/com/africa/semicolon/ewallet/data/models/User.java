package com.africa.semicolon.ewallet.data.models;

import com.africa.semicolon.ewallet.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is not required")
    private String firstName;
    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is not required")
    private String lastName;

    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is not required")
    @Email(message = "This field requires a valid email address")
    private String emailAddress;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Boolean isDisabled = true;


    public User(String firstName, String lastName, String emailAddress,  String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.role = role;
    }
}
