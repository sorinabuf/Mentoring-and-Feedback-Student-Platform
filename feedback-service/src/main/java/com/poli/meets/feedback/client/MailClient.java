package com.poli.meets.feedback.client;

import com.poli.meets.feedback.service.dto.EmailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "notifications-service")
public interface MailClient {

    @PostMapping("/notifications/send-email")
    void sendEmail(@RequestBody EmailDTO emailDTO);
}
