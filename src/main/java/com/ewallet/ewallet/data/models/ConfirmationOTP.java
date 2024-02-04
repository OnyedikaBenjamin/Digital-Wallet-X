package com.ewallet.ewallet.data.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ConfirmationOTP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String oTP;

    private LocalDateTime createdAt;

    private LocalDateTime expiredAt;
    private LocalDateTime confirmedAt;
    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public ConfirmationOTP(String oTP, LocalDateTime createdAt, LocalDateTime expiredAt, User user) {
        this.oTP= oTP;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.user = user;
    }
}


