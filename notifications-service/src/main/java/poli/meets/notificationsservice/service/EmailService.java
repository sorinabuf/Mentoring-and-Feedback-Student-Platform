package poli.meets.notificationsservice.service;


import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import poli.meets.notificationsservice.service.dto.EmailDTO;

import java.io.IOException;

@Service
@Slf4j
public class EmailService {

    @Value("${SENDGRID_API_KEY}")
    private String sendgridAPIKey;

    @Value("${SENDGRID_VERIFIED_SENDER}")
    private String sendgridVerifiedSender;

    public void sendEmail(EmailDTO emailDTO) throws IOException {
        Mail mail = new Mail();

        Personalization personalization = new Personalization();
        personalization.addTo(new Email(emailDTO.getTo()));

        mail.setFrom(new Email(sendgridVerifiedSender));
        mail.setSubject(emailDTO.getSubject());
        mail.addContent(new Content("text/html", emailDTO.getBody()));
        mail.addPersonalization(personalization);


        SendGrid sg = new SendGrid(sendgridAPIKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sg.api(request);

            log.info("Email sent to {} with body {}", emailDTO.getTo(), request.getBody());
        } catch (IOException e) {
            log.error("Failed to send mail to {}.", emailDTO.getTo());
            throw e;
        }
    }
}
