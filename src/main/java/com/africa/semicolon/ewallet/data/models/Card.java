package com.africa.semicolon.ewallet.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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

    private Long cardNumber;

    private LocalDate expiryDate;

    @NotNull(message = "This field is required")
    @NotBlank(message = "This field is required")
    private String cvv;



}
