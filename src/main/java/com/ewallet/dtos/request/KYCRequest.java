package com.ewallet.dtos.request;

import com.ewallet.enums.MeansOfID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KYCRequest {
    private MeansOfID meansOfID;
    private String homeAddress;

}
