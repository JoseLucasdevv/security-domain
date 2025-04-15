package app.security.mailProvider;

import app.security.domain.SetNewEmailToken;

public interface SendEmailService {
    void sendConfirmationLink(String token);
    void sendForgotPasswordLink(String token);
    void SendCodeVerifyEmail(String code);
    void sendChangeEmailUpdate(SetNewEmailToken setNewEmailToken);

}
