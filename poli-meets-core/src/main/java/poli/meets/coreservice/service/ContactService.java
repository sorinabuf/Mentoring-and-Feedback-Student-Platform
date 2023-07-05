package poli.meets.coreservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poli.meets.coreservice.client.MailClient;
import poli.meets.coreservice.service.dto.ContactDTO;
import poli.meets.coreservice.service.dto.EmailDTO;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class ContactService {
    private final MailClient mailClient;

    public void sendMessage(ContactDTO contactDTO) {
        EmailDTO emailDTO = new EmailDTO();

        emailDTO.setTo("poli.meets.app@gmail.com");
        emailDTO.setSubject("User Message");
        emailDTO.setBody(contactDTO.getName() + " sent the following " +
                "message:<br><br>" + '"' + contactDTO.getMessage() + '"' +
                        "<br><br>" + "Contact information: " + contactDTO.getEmail());

        mailClient.sendEmail(emailDTO);
    }
}
