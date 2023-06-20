package poli.meets.coreservice.producer;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import poli.meets.coreservice.service.dto.FacultyDTO;
import poli.meets.coreservice.service.dto.StudentDTO;
import poli.meets.coreservice.service.dto.UniversityYearDTO;

@Service
@AllArgsConstructor
public class UniversityYearProducer {
    private static final String TOPIC = "university_years";

    private final KafkaTemplate<String, UniversityYearDTO> kafkaTemplate;

    public void createOrUpdate(UniversityYearDTO universityYearDTO) {
        this.kafkaTemplate.send(TOPIC, universityYearDTO);
    }

    public void delete(Long id) {
        UniversityYearDTO universityYearDTO = new UniversityYearDTO();
        universityYearDTO.setId(id);

        this.kafkaTemplate.send("university-years-delete", universityYearDTO);
    }

}
