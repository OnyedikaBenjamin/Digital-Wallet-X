package com.ewallet.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String transactionDescription;
    private BigDecimal transactionAmount;
    @CreationTimestamp
    private Instant createdTime = Instant.now();
    @ManyToOne
    @JsonIgnore
    private User user;
}
