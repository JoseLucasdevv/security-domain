package app.security.mailProvider;

import app.security.domain.CodeVerifyEmail;
import app.security.domain.EmailConfirmationToken;
import app.security.domain.ForgotPasswordToken;
import app.security.domain.SetNewEmailToken;
import app.security.exceptions.Exception;
import app.security.repository.CodeVerifyEmailRepository;
import app.security.repository.EmailConfirmationTokenRepository;
import app.security.repository.ForgotPasswordTokenRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class SendEmailServiceImpl implements SendEmailService {
    private final CodeVerifyEmailRepository codeVerifyEmailRepository;
    private final ForgotPasswordTokenRepository forgotTokenRepository;
    private final EmailConfirmationTokenRepository emailConfirmationTokenRepository;
    private final JavaMailSender sender;


    @Override
    public void sendConfirmationLink(String token) {
        try{
        EmailConfirmationToken emailConfirmationToken = this.emailConfirmationTokenRepository.
                findEmailConfirmationTokenByToken(token).orElseThrow(() -> new Exception("Can't find this EmailConfirmationToken",HttpStatus.NOT_FOUND));


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
            throw new Exception(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void sendForgotPasswordLink(String token) {

        try{
            // code review --> maybe it doesn't need visit the database.
            ForgotPasswordToken forgotPasswordToken = this.forgotTokenRepository.findByToken(token).orElseThrow(() -> new Exception("Can't find this EmailConfirmationToken", HttpStatus.NOT_FOUND));


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
                            "<input name=\"newPassword\" title=\"one number and one uppercase and lowercase letter, and at least 8 or more characters\" type=\"password\" required/><br><br>" +
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
            throw new Exception(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
            }

    @Override
    public void SendCodeVerifyEmail(String code) {
        // logic send code to email
        try{
            CodeVerifyEmail codeVerifyEmail = this.codeVerifyEmailRepository.findCodeVerifyEmailByCode(code).orElseThrow(()-> new Exception("Can't find this code",HttpStatus.BAD_REQUEST));
            if(!codeVerifyEmail.getUser().getEmailConfirmed()) throw new Exception("you don't even has an email confirmed",HttpStatus.BAD_REQUEST);

            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(codeVerifyEmail.getUser().getEmail());
            helper.setSubject("Send Verification Code - WorkoutAPI Registration");
            helper.setText("<html>" +
                            "<body>" +
                            "<h2>Dear " + codeVerifyEmail.getUser().getName() + ",</h2>" +
                            "<br/>" +
                            "<h1>Your Code Verification is:" + codeVerifyEmail.getCode() + "</h1>" +
                            "<p>Se você não solicitou esta alteração, ignore este e-mail.</p>" +
                            "<br>Regards,<br>" +
                            "WorkoutAPI team" +

                            "</body>" +
                            "</html>",
                    true);


            sender.send(message);
        } catch (MessagingException e) {
            throw new Exception(e.getMessage(),HttpStatus.BAD_REQUEST);
        }


    }

    @Override
    public void sendChangeEmailUpdate(SetNewEmailToken setNewEmailToken) {
        try{


            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(setNewEmailToken.getNewEmail());
            helper.setSubject("UpdateEmail - WorkoutAPI");
            helper.setText("<html>" +
                            "<body>" +
                            "<h2>Dear "+ setNewEmailToken.getUser().getName() + ",</h2>" +
                            "Please click on below link to Change Email from your account."
                            + "<br/> "  + generateConfirmationLinkChangeEmail(setNewEmailToken.getToken())+"" +
                            "<br/> Regards,<br/>" +
                            "WorkoutAPI team" +
                            "</body>" +
                            "</html>"
                    , true);

            sender.send(message);
        } catch (MessagingException e) {
            throw new Exception(e.getMessage(),HttpStatus.BAD_REQUEST);
        }


    }

    private String generateConfirmationLinkChangeEmail(String token) {
        return "<a href=http://localhost:8080/api/user/change-email?token=" + token + ">Change Email</a>";
    }

        private String generateConfirmationLink(String token){
        return "<a href=http://localhost:8080/api/confirm-email?token="+token+">Confirm Email</a>";
    }


    private String createActionForm(String token){
        return "http://localhost:8080/api/reset-password?token="+token;
    }
}


