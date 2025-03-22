package app.security.services;

import app.security.domain.ForgotPasswordToken;
import app.security.domain.User;
import app.security.exceptions.Exception;
import app.security.repository.ForgotPasswordTokenRepository;
import app.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class ResetPasswordServiceImpl implements ResetPasswordService {
private final UserRepository userRepository;
private final ForgotPasswordTokenRepository forgotPasswordTokenRepository;

    @Override
    public void resetPassword(String token,String newPassword) {
            ForgotPasswordToken forgotPasswordToken = forgotPasswordTokenRepository.findByToken(token)
                    .orElseThrow(()-> new Exception("can't find this token"));

            Instant expiresAt = forgotPasswordToken.getExpiresAt();
            if(expiresAt.isBefore(Instant.now())){
                forgotPasswordTokenRepository.delete(forgotPasswordToken);
                throw new Exception("the token was Expired");
            };
            User usr = forgotPasswordToken.getUser();
            String encryptedPassword = new BCryptPasswordEncoder().encode(newPassword);
            usr.setPassword(encryptedPassword);
            forgotPasswordTokenRepository.delete(forgotPasswordToken);
            usr.getForgotPasswordTokens().removeIf(t ->t.getExpiresAt().isBefore(Instant.now()));
            userRepository.save(usr);


    }
}
