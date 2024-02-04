package com.ewallet.ewallet.services.registration;


import com.ewallet.ewallet.dtos.request.ConfirmTokenRequest;
import com.ewallet.ewallet.dtos.request.RegistrationRequest;
import com.ewallet.ewallet.dtos.request.ResendTokenRequest;

public interface RegistrationService {

    public String register(RegistrationRequest registrationRequest);

    public String confirmToken(ConfirmTokenRequest confirmTokenRequest);

    public String resendConfirmationToken(ResendTokenRequest resendTokenRequest);


}
