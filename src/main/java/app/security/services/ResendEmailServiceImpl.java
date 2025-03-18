package app.security.services;

import app.security.domain.EmailConfirmationToken;
import app.security.domain.User;
import app.security.mailProvider.SendEmailService;
import app.security.repository.EmailConfirmationTokenRepository;
import app.security.repository.UserRepository;
import app.security.utils.GenerateExpirationDate;
import app.security.utils.GenerateKeyEncoded;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResendEmailServiceImpl implements ResendEmailService {
    @Autowired
    EmailConfirmationTokenRepository emailConfirmationTokenRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private SendEmailService sendEmailService;


    @Override
    public void resendEmailConfirmation(Boolean userEmailConfirm,String username) throws Exception {
    if(userEmailConfirm) throw new Exception("email from user Already confirmed");
    User user = this.userRepository.findByUsername(username);
    user.getEmailConfirmationTokens().removeIf(t ->t.getExpiresAt().isBefore(java.time.Instant.now()));
    String tokenEmailConfirm = GenerateKeyEncoded.getKeyEncodedToken();


    EmailConfirmationToken emailConfirmationToken = new EmailConfirmationToken();

    emailConfirmationToken.setUser(user);
        emailConfirmationToken.setToken(tokenEmailConfirm);
        emailConfirmationToken.setExpiresAt(GenerateExpirationDate.genExpirationDate(2L));
        this.emailConfirmationTokenRepository.save(emailConfirmationToken);
        this.sendEmailService.sendConfirmationLink(emailConfirmationToken.getToken());

    }
}
