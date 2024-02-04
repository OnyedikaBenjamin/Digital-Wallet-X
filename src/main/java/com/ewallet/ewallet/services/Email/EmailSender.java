package com.ewallet.ewallet.services.Email;

import jakarta.mail.MessagingException;

public interface EmailSender {

    void send(String to, String email)throws MessagingException;
}
