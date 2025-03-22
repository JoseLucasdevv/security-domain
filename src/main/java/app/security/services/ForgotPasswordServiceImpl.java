package app.security.services;

import app.security.domain.ForgotPasswordToken;
import app.security.domain.User;
import app.security.dto.EmailForgotPasswordIn;
import app.security.exceptions.Exception;
import app.security.mailProvider.SendEmailService;
import app.security.repository.ForgotPasswordTokenRepository;
import app.security.repository.UserRepository;
import app.security.utils.GenerateExpirationDate;
import app.security.utils.GenerateKeyEncoded;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    @Autowired
    private SendEmailService sendEmailService;
    @Autowired
    private ForgotPasswordTokenRepository forgotPasswordTokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public void send(EmailForgotPasswordIn input) {
        User user = userRepository.findByEmail(input.email());
        if(user == null ) throw new Exception("we sent the email to your email address");
        ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken();
        forgotPasswordToken.setUser(user);
        forgotPasswordToken.setExpiresAt(GenerateExpirationDate.genExpirationDate(2L));
        forgotPasswordToken.setToken(GenerateKeyEncoded.getKeyEncodedToken());
        forgotPasswordTokenRepository.save(forgotPasswordToken);
        sendEmailService.sendForgotPasswordLink(forgotPasswordToken.getToken());}





}
