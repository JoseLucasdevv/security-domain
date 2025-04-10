package app.security.services;

import app.security.domain.CodeVerifyEmail;
import app.security.domain.User;
import app.security.exceptions.Exception;
import app.security.mailProvider.SendEmailService;
import app.security.repository.CodeVerifyEmailRepository;
import app.security.repository.EmailConfirmationTokenRepository;
import app.security.repository.UserRepository;
import app.security.utils.GenerateCodeVerification;
import app.security.utils.GenerateExpirationDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private SendEmailService sendEmailService;
    @Autowired
    private CodeVerifyEmailRepository codeVerifyEmailRepository;
    @Autowired
    private EmailConfirmationTokenRepository emailConfirmationTokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public void verifyTokenEmailConfirmation(String token) {
        var tokenEmailConfirmation = emailConfirmationTokenRepository
                .findEmailConfirmationTokenByToken(token)
                .orElseThrow(()->new Exception("the token is Invalid",HttpStatus.FORBIDDEN));
        if(tokenEmailConfirmation.getExpiresAt().isBefore(Instant.now())) throw new Exception("the token was expired", HttpStatus.FORBIDDEN);

        User user = tokenEmailConfirmation.getUser();
        user.setEmailConfirmed(true);
        this.userRepository.save(user);

    }

    @Override
    public void sendVerificationCode(String username) {
        User user = this.userRepository.findByUsername(username);
        CodeVerifyEmail codeVerify = new CodeVerifyEmail();
        codeVerify.setUser(user);
        Instant expires = GenerateExpirationDate.genExpirationDate(2L);
        codeVerify.setExpiresAt(expires);
        String codeGenerated = GenerateCodeVerification.GenerateCode();
        codeVerify.setCode(codeGenerated);
        this.codeVerifyEmailRepository.save(codeVerify);
        this.sendEmailService.SendCodeVerifyEmail(codeVerify.getCode());

    }
    @Override
    public void verifyCode(String code) {


        return null;
    }
}
