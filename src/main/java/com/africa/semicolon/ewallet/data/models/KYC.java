package com.africa.semicolon.ewallet.data.models;

import com.africa.semicolon.ewallet.enums.MeansOfID;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    private String homeAddress;
    private Long BVN;
}
