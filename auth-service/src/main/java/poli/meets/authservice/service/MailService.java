package poli.meets.authservice.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@AllArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    public void sendActivationEmail(String email, String activationUrl) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Activate your account");
        message.setText("Click the following link to activate your account: " + activationUrl);
        javaMailSender.send(message);
    }
}