package com.poli.meets.mentorship.consumer;

import com.poli.meets.mentorship.domain.Faculty;
import com.poli.meets.mentorship.domain.UniversityYear;
import com.poli.meets.mentorship.repository.FacultyRepository;
import com.poli.meets.mentorship.repository.UniversityYearRepository;
import com.poli.meets.mentorship.service.mapper.FacultyMapper;
import com.poli.meets.mentorship.service.mapper.UniversityYearMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import poli.meets.coreservice.service.dto.FacultyDTO;
import poli.meets.coreservice.service.dto.UniversityYearDTO;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class FacultyConsumer {

    private final FacultyRepository facultyRepository;

    private final FacultyMapper facultyMapper;

    @KafkaListener(topics = "faculties", groupId = "mentorship-group")
    public void consume(FacultyDTO facultyDTO) throws IOException {

        Faculty newFaculty = facultyMapper.toEntity(facultyDTO);
        Optional<Faculty> oldFaculty = facultyRepository
                .findByExternalId(facultyDTO.getId());

        if (oldFaculty.isPresent()) {
            newFaculty.setId(oldFaculty.get().getId());
        }

        newFaculty = facultyRepository.save(newFaculty);
    }

    @KafkaListener(topics = "faculties-delete", groupId = "mentorship-group")
    public void delete(FacultyDTO facultyDTO) throws IOException {
        facultyRepository.findByExternalId(facultyDTO.getId()).ifPresent(facultyRepository::delete);
    }
}
