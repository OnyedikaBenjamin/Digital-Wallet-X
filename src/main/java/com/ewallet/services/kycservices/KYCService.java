package com.ewallet.services.kycservices;

import com.ewallet.data.models.KYC;
import com.ewallet.dtos.request.KYCRequest;

public interface KYCService {
    KYC addKYC(KYCRequest kycRequest);

}
