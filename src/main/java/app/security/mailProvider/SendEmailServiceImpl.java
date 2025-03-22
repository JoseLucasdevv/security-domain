package app.security.mailProvider;

import app.security.domain.EmailConfirmationToken;
import app.security.domain.ForgotPasswordToken;
import app.security.exceptions.Exception;
import app.security.repository.EmailConfirmationTokenRepository;
import app.security.repository.ForgotPasswordTokenRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class SendEmailServiceImpl implements SendEmailService {
    private final ForgotPasswordTokenRepository forgotTokenRepository;
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

    @Override
    public void sendForgotPasswordLink(String token) {

        try{
            ForgotPasswordToken forgotPasswordToken = this.forgotTokenRepository.findByToken(token).orElseThrow(() -> new Exception("Can't find this EmailConfirmationToken"));


            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(forgotPasswordToken.getUser().getEmail());
            helper.setSubject("Reset Password - WorkoutAPI Registration");
            helper.setText("<html>" +
                            "<body>" +
                            "<h2>Dear " + forgotPasswordToken.getUser().getName() + ",</h2>" +
                            "<p>Você solicitou a recuperação de sua senha. Use o formulário abaixo para definir uma nova senha:</p>" +
                            "<form action=\"" + createActionForm(token) + "\" method=\"post\">" +
                            "<label for=\"newPassword\">Nova senha:</label><br>" +
                            "<input name=\"newPassword\" pattern=\"(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{8,}\" title=\"one number and one uppercase and lowercase letter, and at least 8 or more characters\" type=\"password\" required/><br><br>" +
                            "<button class=\"submitBtn\" type=\"submit\">Reset Password</button>" +
                            "</form>" +
                            "<p>Se você não solicitou esta alteração, ignore este e-mail.</p>" +
                            "<br>Regards,<br>" +
                            "WorkoutAPI team" +

                            "</body>" +
                            "</html>",
                    true);


            sender.send(message);
        } catch (MessagingException e) {
            throw new Exception(e.getMessage());
        }
            }

    private String generateConfirmationLink(String token){
        return "<a href=http://localhost:8080/api/confirm-email?token="+token+">Confirm Email</a>";
    }


    private String createActionForm(String token){
        return "http://localhost:8080/api/reset-password?token="+token;
    }
}


