package com.africa.semicolon.ewallet.data.models;

import com.africa.semicolon.ewallet.enums.Role;
<<<<<<< HEAD
=======
import com.fasterxml.jackson.annotation.JsonIgnore;
>>>>>>> aee9af0e7b39e430cb08826f8cf583fa2f823c76
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

<<<<<<< HEAD
=======
import java.util.List;

>>>>>>> aee9af0e7b39e430cb08826f8cf583fa2f823c76
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
<<<<<<< HEAD
=======
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id", referencedColumnName = "id")
    private List<Card> cardList ;
    @OneToOne
    @JoinColumn(name = "nextOfKin_Id", referencedColumnName = "id")
    private NextOfKin nextOfKin;
    @OneToOne
    @JoinColumn(name = "kyc_id", referencedColumnName = "id")
    private KYC kyc;

>>>>>>> aee9af0e7b39e430cb08826f8cf583fa2f823c76

    public User(String firstName, String lastName, String emailAddress,  String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.role = role;
    }
}
