package com.poli.meets.feedback.consumer;

import com.poli.meets.feedback.domain.Faculty;
import com.poli.meets.feedback.repository.FacultyRepository;
import com.poli.meets.feedback.service.mapper.FacultyMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import poli.meets.coreservice.service.dto.FacultyDTO;
import poli.meets.coreservice.service.dto.StudentDTO;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class FacultyConsumer {

    private final FacultyRepository facultyRepository;

    private final FacultyMapper facultyMapper;

    @KafkaListener(topics = "faculties", groupId = "feedback-group")
    public void consume(FacultyDTO facultyDTO) throws IOException {

        Faculty newFaculty = facultyMapper.toEntity(facultyDTO);
        Optional<Faculty> oldFaculty = facultyRepository
                .findByExternalId(facultyDTO.getId());

        if (oldFaculty.isPresent()) {
            newFaculty.setId(oldFaculty.get().getId());
        }

        newFaculty = facultyRepository.save(newFaculty);
    }

    @KafkaListener(topics = "faculties-delete", groupId = "feedback-group")
    public void delete(FacultyDTO studentDTO) throws IOException {
        facultyRepository.findByExternalId(studentDTO.getId()).ifPresent(facultyRepository::delete);
    }
}
