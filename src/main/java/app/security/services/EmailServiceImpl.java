package app.security.services;

import app.security.domain.ChangeEmailToken;
import app.security.domain.CodeVerifyEmail;
import app.security.domain.SetNewEmailToken;
import app.security.domain.User;
import app.security.dto.ChangeEmailDTO;
import app.security.exceptions.Exception;
import app.security.mailProvider.SendEmailService;
import app.security.repository.*;
import app.security.utils.GenerateCodeVerification;
import app.security.utils.GenerateExpirationDate;
import app.security.utils.GenerateKeyEncoded;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SetNewEmailTokenRepository setNewEmailTokenRepository;
    @Autowired
    private ChangeEmailTokenRepository  changeEmailTokenRepository;
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
    public ChangeEmailToken verifyCode(String code, String username) {
        // use CodeVerifyEmailRepository instead UserRepository

        User user = this.userRepository.findByUsername(username);
        CodeVerifyEmail codeVerifyEmail = user.getCodeVerifyEmail()
                .stream()
                .filter(entity -> entity.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new Exception("we can't find this token",HttpStatus.NOT_FOUND));
        if(codeVerifyEmail.getExpiresAt().isBefore(Instant.now())) throw new Exception("this code has already expired",HttpStatus.NOT_ACCEPTABLE);
        user.getCodeVerifyEmail().clear();
        ChangeEmailToken changeEmailToken = new ChangeEmailToken();
        String token = GenerateKeyEncoded.getKeyEncodedToken();
        changeEmailToken.setToken(token);
        changeEmailToken.setUser(user);
        changeEmailToken.setExpiresAt(GenerateExpirationDate.genExpirationDate(20L));
        changeEmailTokenRepository.save(changeEmailToken);
        // create key token to send to the user.
        return changeEmailToken;
    }

    @Override
    public void emailSenderChangeEmail(String token, ChangeEmailDTO contentBody) {
     ChangeEmailToken changeEmailToken = this.changeEmailTokenRepository.
             findByToken(token).
             orElseThrow(() -> new Exception("Oops, Something wrong !",HttpStatus.NOT_FOUND));

            if(changeEmailToken.getExpiresAt().isBefore(Instant.now())) throw new Exception("the token was expired",HttpStatus.BAD_REQUEST);
            User user = changeEmailToken.getUser();

            User userFound = this.userRepository.findByEmail(contentBody.newEmail());
            if(userFound != null) throw new Exception("there is User that already use this email",HttpStatus.FOUND);

            boolean matcher = this.passwordEncoder.matches(contentBody.password(), user.getPassword());


            if(!matcher) throw new Exception("your password isn't correct",HttpStatus.NOT_FOUND);
            SetNewEmailToken setNewEmailToken = new SetNewEmailToken();
            String tokenSetEmail = GenerateKeyEncoded.getKeyEncodedToken();
            setNewEmailToken.setToken(tokenSetEmail);
            setNewEmailToken.setUser(user);
            setNewEmailToken.setExpiresAt(GenerateExpirationDate.genExpirationDate(1L));
            setNewEmailToken.setNewEmail(contentBody.newEmail());
            setNewEmailTokenRepository.save(setNewEmailToken);
            this.sendEmailService.sendChangeEmailUpdate(setNewEmailToken);


    }
}
