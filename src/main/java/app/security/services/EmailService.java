package app.security.services;

import app.security.domain.ChangeEmailToken;
import app.security.dto.ChangeEmailDTO;

public interface EmailService {
    void verifyTokenEmailConfirmation(String token);
    void sendVerificationCode(String username);
    ChangeEmailToken verifyCode(String code, String username);
    void emailSenderChangeEmail(String token, ChangeEmailDTO contentBody);

}
