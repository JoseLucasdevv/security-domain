package app.security.mailProvider;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class SendEmailServiceImpl implements SendEmailService {

    private final JavaMailSender sender;


}
