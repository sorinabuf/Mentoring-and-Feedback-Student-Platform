package poli.meets.coreservice.producer;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import poli.meets.coreservice.service.dto.FacultyDTO;

@Service
@AllArgsConstructor
public class FacultyProducer {
    private static final String TOPIC = "faculties";

    private final KafkaTemplate<String, FacultyDTO> kafkaTemplate;

    public void createOrUpdate(FacultyDTO facultyDTO) {
        this.kafkaTemplate.send(TOPIC, facultyDTO);
    }

    public void delete(Long id) {
        FacultyDTO facultyDTO = new FacultyDTO();
        facultyDTO.setId(id);

        this.kafkaTemplate.send("faculties-delete", facultyDTO);
    }

}
