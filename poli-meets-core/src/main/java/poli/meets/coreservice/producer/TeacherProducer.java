package poli.meets.coreservice.producer;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import poli.meets.coreservice.service.dto.FacultyDTO;
import poli.meets.coreservice.service.dto.TeacherDTO;

@Service
@AllArgsConstructor
public class TeacherProducer {
    private static final String TOPIC = "teachers";

    private final KafkaTemplate<String, TeacherDTO> kafkaTemplate;

    public void createOrUpdate(TeacherDTO teacherDTO) {
        this.kafkaTemplate.send(TOPIC, teacherDTO);
    }

    public void delete(Long id) {
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setId(id);

        this.kafkaTemplate.send("teachers-delete", teacherDTO);
    }

}
