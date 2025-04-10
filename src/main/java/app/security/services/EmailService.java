package app.security.services;

public interface EmailService {
    void verifyTokenEmailConfirmation(String token);
    void sendVerificationCode(String username);
    void verifyCode(String code);
}
