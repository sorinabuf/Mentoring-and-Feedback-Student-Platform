package poli.meets.coreservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import poli.meets.coreservice.service.dto.EmailDTO;


@FeignClient(name = "notifications-service")
public interface MailClient {

    @PostMapping("/notifications/send-email")
    void sendEmail(@RequestBody EmailDTO emailDTO);
}
