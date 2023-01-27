package com.africa.semicolon.ewallet.data.models;

import com.africa.semicolon.ewallet.enums.CardStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import static com.africa.semicolon.ewallet.enums.CardStatus.ACTIVE;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "This field is required")
    @NotBlank(message = "This field is required")
    private String cardName;
    @NotEmpty(message = "This field is not required")
    @NotBlank(message = "This field is required")
    private String cardNumber;
    private String expiryDate;

    @NotNull(message = "This field is required")
    @NotBlank(message = "This field is required")
    private String cvv;
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private CardStatus cardStatus = ACTIVE;



}
