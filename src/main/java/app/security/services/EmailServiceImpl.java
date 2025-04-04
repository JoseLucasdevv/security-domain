package app.security.services;

import app.security.domain.User;
import app.security.exceptions.Exception;
import app.security.repository.EmailConfirmationTokenRepository;
import app.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class EmailServiceImpl implements EmailService {
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
}
