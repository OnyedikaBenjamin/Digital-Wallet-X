package com.ewallet.services.nextofkinservices;

import com.ewallet.data.models.NextOfKin;
import com.ewallet.dtos.request.NextOfKinRequest;

public interface NextOfKinService {
    NextOfKin addNextOfKin(NextOfKinRequest nextOfKinRequest);
}
