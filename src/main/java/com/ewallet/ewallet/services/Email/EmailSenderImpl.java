package com.ewallet.ewallet.services.Email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j

public class EmailSenderImpl implements EmailSender{

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void send(String to, String email) throws MessagingException {

        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, mimeMessage.getEncoding());
            mimeMessageHelper.setSubject("Confirm your email");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom("BenBillion@gmail.com");
            javaMailSender.send(mimeMessage);

        }catch(MessagingException e){
            log.info("problem2: ");
            log.info(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
