package com.poli.meets.feedback.consumer;


import com.poli.meets.feedback.domain.Faculty;
import com.poli.meets.feedback.domain.UniversityYear;
import com.poli.meets.feedback.repository.FacultyRepository;
import com.poli.meets.feedback.repository.UniversityYearRepository;
import com.poli.meets.feedback.service.mapper.FacultyMapper;
import com.poli.meets.feedback.service.mapper.UniversityYearMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import poli.meets.coreservice.service.dto.StudentDTO;
import poli.meets.coreservice.service.dto.UniversityYearDTO;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UniversityYearConsumer {

    private final UniversityYearRepository universityYearRepository;

    private final FacultyRepository facultyRepository;

    private final UniversityYearMapper universityYearMapper;

    private final FacultyMapper facultyMapper;

    @KafkaListener(topics = "university_years", groupId = "feedback-group")
    public void consume(UniversityYearDTO universityYearDTO) throws IOException {

        Faculty newFaculty = facultyMapper.toEntity(universityYearDTO.getFaculty());
        Optional<Faculty> oldFaculty = facultyRepository
                .findByExternalId(universityYearDTO.getFaculty().getId());

        if (oldFaculty.isPresent()) {
            newFaculty.setId(oldFaculty.get().getId());
        }

        newFaculty = facultyRepository.save(newFaculty);

        UniversityYear newUniversityYear = universityYearMapper.toEntity(universityYearDTO);
        newUniversityYear.setFaculty(newFaculty);

        Optional<UniversityYear> oldUniversityYear = universityYearRepository
                .findByExternalId(universityYearDTO.getId());

        if (oldUniversityYear.isPresent()) {
            newUniversityYear.setId(oldUniversityYear.get().getId());
        }

        newUniversityYear = universityYearRepository.save(newUniversityYear);
    }

    @KafkaListener(topics = "university-years-delete", groupId = "feedback-group")
    public void delete(UniversityYearDTO universityYearDTO) throws IOException {
        universityYearRepository.findByExternalId(universityYearDTO.getId()).ifPresent(universityYearRepository::delete);
    }
}
