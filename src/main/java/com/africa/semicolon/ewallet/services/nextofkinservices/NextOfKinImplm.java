package com.africa.semicolon.ewallet.services.nextofkinservices;


import com.africa.semicolon.ewallet.data.models.NextOfKin;
import com.africa.semicolon.ewallet.data.repositories.NextOfKinRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class NextOfKinImplm implements NextOfKinService {
    @Autowired
    private NextOfKinRepo nextOfKinRepo;

    @Override
    public NextOfKin addNextOfKin(NextOfKin nextOfKin) {
        return nextOfKinRepo.save(nextOfKin);
    }
}
