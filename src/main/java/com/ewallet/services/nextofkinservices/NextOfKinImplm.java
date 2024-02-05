package com.ewallet.services.nextofkinservices;


import com.ewallet.data.models.NextOfKin;
import com.ewallet.data.repositories.NextOfKinRepo;
import com.ewallet.dtos.request.NextOfKinRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class NextOfKinImplm implements NextOfKinService {
    @Autowired
    private NextOfKinRepo nextOfKinRepo;

    @Override
    public NextOfKin addNextOfKin(NextOfKinRequest nextOfKinRequest) {
        NextOfKin nextOfKin = new NextOfKin();
        nextOfKin.setFullName(nextOfKinRequest.getFullName());
        nextOfKin.setEmail(nextOfKinRequest.getEmail());
        nextOfKin.setPhoneNumber(nextOfKinRequest.getPhoneNumber());
        nextOfKin.setRelationship(nextOfKinRequest.getRelationship());
        return nextOfKinRepo.save(nextOfKin);
    }
}
