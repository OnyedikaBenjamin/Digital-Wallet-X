package com.africa.semicolon.ewallet.data.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @NotEmpty(message = "This field is required")
    @NotBlank(message = "This field is required")
    private String cardNumber;
    private String expiryDate;
    @NotNull(message = "This field is required")
    @NotBlank(message = "This field is required")
    private String cvv;
    @ManyToOne
    @JsonIgnore
    private User user;
}
