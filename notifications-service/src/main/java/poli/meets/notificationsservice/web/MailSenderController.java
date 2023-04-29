package poli.meets.notificationsservice.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import poli.meets.notificationsservice.service.EmailService;
import poli.meets.notificationsservice.service.dto.EmailDTO;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class MailSenderController {

    private final EmailService emailService;

    @PostMapping("/send-email")
    public void sendEmail(@RequestBody EmailDTO emailDTO) throws IOException {
        emailService.sendEmail(emailDTO);
    }
}
