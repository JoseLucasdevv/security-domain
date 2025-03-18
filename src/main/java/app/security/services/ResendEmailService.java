package app.security.services;

public interface ResendEmailService {
    void resendEmailConfirmation(Boolean userEmailConfirm,String username) throws Exception;
}
