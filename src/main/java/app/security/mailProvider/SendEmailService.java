package app.security.mailProvider;

public interface SendEmailService {
    void sendConfirmationLink(String token);

}
