package com.africa.semicolon.ewallet.services.nextofkinservices;

import com.africa.semicolon.ewallet.data.models.NextOfKin;
import com.africa.semicolon.ewallet.dtos.request.NextOfKinRequest;

public interface NextOfKinService {
    NextOfKin addNextOfKin(NextOfKinRequest nextOfKinRequest);
}
