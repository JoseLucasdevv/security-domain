package app.security.services;

public interface ResetPasswordService {
    void resetPassword(String token,String newPassword);
}
