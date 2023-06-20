package poli.meets.coreservice.producer;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import poli.meets.coreservice.service.dto.FacultyDTO;
import poli.meets.coreservice.service.dto.UniversityClassDTO;

@Service
@AllArgsConstructor
public class UniversityClassProducer {
    private static final String TOPIC = "unviersity_classes";

    private final KafkaTemplate<String, UniversityClassDTO> kafkaTemplate;

    public void createOrUpdate(UniversityClassDTO universityClassDTO) {
        this.kafkaTemplate.send(TOPIC, universityClassDTO);
    }

    public void delete(Long id) {
        UniversityClassDTO universityClassDTO = new UniversityClassDTO();
        universityClassDTO.setId(id);

        this.kafkaTemplate.send("university-classes-delete", universityClassDTO);
    }

}
