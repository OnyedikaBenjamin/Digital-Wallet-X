package com.africa.semicolon.ewallet.services.kycservices;

import com.africa.semicolon.ewallet.data.models.KYC;
import com.africa.semicolon.ewallet.dtos.request.KYCRequest;

public interface KYCService {
    KYC addKYC(KYCRequest kycRequest);

}
