package app.security.services;

import app.security.dto.EmailForgotPasswordIn;

public interface ForgotPasswordService {
    void send(EmailForgotPasswordIn input);

}
