package poli.meets.coreservice.producer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.kafka.core.KafkaTemplate;
import poli.meets.coreservice.service.dto.StudentDTO;

@Service
@AllArgsConstructor
public class Producer {

    private static final String TOPIC = "students";

    private final KafkaTemplate<String, StudentDTO> kafkaTemplate;

    public void createOrUpdateStudent(StudentDTO studentDTO) {
        this.kafkaTemplate.send(TOPIC, studentDTO);
    }

}
