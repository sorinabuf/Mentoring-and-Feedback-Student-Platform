package poli.meets.coreservice.producer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.kafka.core.KafkaTemplate;
import poli.meets.coreservice.service.dto.FacultyDTO;
import poli.meets.coreservice.service.dto.StudentDTO;

@Service
@AllArgsConstructor
public class StudentProducer {

    private static final String TOPIC = "students";

    private final KafkaTemplate<String, StudentDTO> kafkaTemplate;

    public void createOrUpdateStudent(StudentDTO studentDTO) {
        this.kafkaTemplate.send(TOPIC, studentDTO);
    }

    public void delete(Long id) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(id);
        this.kafkaTemplate.send("students-delete", studentDTO);
    }

}
