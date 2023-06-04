package com.poli.meets.feedback.consumer;


import poli.meets.coreservice.service.dto.StudentDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class Consumer {

    @KafkaListener(topics = "students", groupId = "feedback_group")
    public void consume(StudentDTO studentDTO) throws IOException {
        log.info(studentDTO.toString());
    }
}
