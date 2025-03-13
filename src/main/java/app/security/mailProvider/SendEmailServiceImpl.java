package app.security.mailProvider;

import app.security.domain.EmailConfirmationToken;
import app.security.exceptions.Exception;
import app.security.repository.EmailConfirmationTokenRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class SendEmailServiceImpl implements SendEmailService {
    private final EmailConfirmationTokenRepository emailConfirmationTokenRepository;
    private final JavaMailSender sender;


    @Override
    public void sendConfirmationLink(String token) {
        try{
        EmailConfirmationToken emailConfirmationToken = this.emailConfirmationTokenRepository.
                findEmailConfirmationTokenByToken(token).orElseThrow(() -> new Exception("Can't find this EmailConfirmationToken"));


        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(emailConfirmationToken.getUser().getEmail());
        helper.setSubject("Confirm you E-Mail - WorkoutAPI Registration");
        helper.setText("<html>" +
                        "<body>" +
                        "<h2>Dear "+ emailConfirmationToken.getUser().getName() + ",</h2>"
                        + "<br/> We're excited to have you get started. " +
                        "Please click on below link to confirm your account."
                        + "<br/> "  + generateConfirmationLink(emailConfirmationToken.getToken())+"" +
                        "<br/> Regards,<br/>" +
                        "WorkoutAPI team" +
                        "</body>" +
                        "</html>"
                , true);

        sender.send(message);
        } catch (MessagingException e) {
            throw new Exception(e.getMessage());
        }
    }

    private String generateConfirmationLink(String token){
        return "<a href=http://localhost:8080/confirm-email?token="+token+">Confirm Email</a>";
    }
}


