package app.security.services;

public interface EmailService {
    void verifyTokenEmailConfirmation(String token);

}
