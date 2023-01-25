package com.africa.semicolon.ewallet.services.resetpassword;

import com.africa.semicolon.ewallet.data.models.User;
import com.africa.semicolon.ewallet.data.models.VerificationOTP;
import com.africa.semicolon.ewallet.dtos.request.ResetPasswordRequest;
import com.africa.semicolon.ewallet.dtos.request.SendOTPRequest;
import com.africa.semicolon.ewallet.dtos.request.VerifyOTPRequest;
import com.africa.semicolon.ewallet.exceptions.GenericHandlerException;
import com.africa.semicolon.ewallet.services.email.EmailSender;
import com.africa.semicolon.ewallet.services.registration.otp.RegistrationService;
import com.africa.semicolon.ewallet.services.registration.otp.VerificationOTPService;
import com.africa.semicolon.ewallet.services.user.UserService;
import com.africa.semicolon.ewallet.utils.OTPGenerator;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class ResetPasswordServiceImpl implements ResetPasswordService{
    @Autowired
    VerificationOTPService verificationOTPService;
    @Autowired
    EmailSender emailSender;
    @Autowired
    UserService userService;
    @Autowired
    RegistrationService registrationService;
    @Override
    public String emailOTP(SendOTPRequest sendOTPRequest) throws MessagingException {

        boolean isExist = userService.findUserByEmailAddress(sendOTPRequest.getEmailAddress())
                .isPresent();
        if (!isExist)throw new GenericHandlerException("Not a user");
        String generatedOTP = OTPGenerator.generateOTP().toString();
        VerificationOTP verificationOTP = new VerificationOTP(
                generatedOTP,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10),
                userService.findUserByEmailAddress(sendOTPRequest.getEmailAddress()).get()
        );
        verificationOTPService.saveVerificationOTP(verificationOTP);
        emailSender.send(sendOTPRequest.getEmailAddress(), buildEmail(
                userService.findUserByEmailAddress(sendOTPRequest.getEmailAddress()).get().getFirstName(),
                generatedOTP
        ));
        return "OTP successfully sent to your email address";
    }

    @Override
    public String verifyOTP(VerifyOTPRequest verifyOTPRequest) {
        VerificationOTP verificationOTP = verificationOTPService.findByOTP(verifyOTPRequest.getOneTimePassword())
                .orElseThrow(()->new GenericHandlerException("Invalid token"));
        if(verificationOTP.getExpiredAt().isBefore(LocalDateTime.now())){
            throw new GenericHandlerException("Token has expired");
        }

        if(verificationOTP.getVerifiedAt() != null){
            throw new GenericHandlerException("Token has been used");
        }
        verificationOTPService.setVerifiedAt(verificationOTP.getOneTimePassword());

        return "verified";
    }

    @Override
    public String resetPassword(ResetPasswordRequest resetPasswordRequest) {
        User foundUser = userService.findUserByEmailAddress(resetPasswordRequest.getEmailAddress()).get();
        if (!Objects.equals(resetPasswordRequest.getPassword(), resetPasswordRequest.getConfirmPassword()))throw new GenericHandlerException(
                "Password doesn't match"
        );
        foundUser.setPassword(resetPasswordRequest.getPassword());
        userService.saveUser(foundUser);
        return "Password changed successfully";
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">" + link + "</p></blockquote>\n Link will expire in 10 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

}
