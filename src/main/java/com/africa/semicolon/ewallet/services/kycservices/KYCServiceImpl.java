package com.africa.semicolon.ewallet.services.kycservices;

import com.africa.semicolon.ewallet.data.models.KYC;
import com.africa.semicolon.ewallet.data.repositories.KYCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KYCServiceImpl implements KYCService{
    @Autowired
    KYCRepository kycRepository;
    @Override
    public KYC addKYC(KYC kyc) {
        return kycRepository.save(kyc);
    }
}
