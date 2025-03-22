package app.security.mailProvider;

public interface SendEmailService {
    void sendConfirmationLink(String token);
    void sendForgotPasswordLink(String token);

}
