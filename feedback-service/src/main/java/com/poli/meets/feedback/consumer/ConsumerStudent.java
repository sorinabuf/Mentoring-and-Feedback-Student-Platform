package com.poli.meets.feedback.consumer;


import com.poli.meets.feedback.domain.Faculty;
import com.poli.meets.feedback.domain.Student;
import com.poli.meets.feedback.domain.UniversityYear;
import com.poli.meets.feedback.repository.FacultyRepository;
import com.poli.meets.feedback.repository.StudentRepository;
import com.poli.meets.feedback.repository.UniversityYearRepository;
import com.poli.meets.feedback.service.mapper.FacultyMapper;
import com.poli.meets.feedback.service.mapper.StudentMapper;
import com.poli.meets.feedback.service.mapper.UniversityYearMapper;
import lombok.AllArgsConstructor;
import poli.meets.coreservice.service.dto.FacultyDTO;
import poli.meets.coreservice.service.dto.StudentDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import poli.meets.coreservice.service.dto.UniversityYearDTO;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ConsumerStudent {

    private final StudentRepository studentRepository;

    private final UniversityYearRepository universityYearRepository;

    private final FacultyRepository facultyRepository;

    private final StudentMapper studentMapper;

    private final UniversityYearMapper universityYearMapper;

    private final FacultyMapper facultyMapper;

    @KafkaListener(topics = "students", groupId = "feedback_group")
    public void consume(StudentDTO studentDTO) throws IOException {

        Faculty newFaculty = facultyMapper.toEntity(studentDTO.getUniversityYear().getFaculty());
        Optional<Faculty> oldFaculty = facultyRepository
                .findByExternalId(studentDTO.getUniversityYear().getFaculty().getId());

        if (oldFaculty.isPresent()) {
                newFaculty.setId(oldFaculty.get().getId());
        }

        newFaculty = facultyRepository.save(newFaculty);

        UniversityYear newUniversityYear = universityYearMapper.toEntity(studentDTO.getUniversityYear());
        Optional<UniversityYear> oldUniversityYear = universityYearRepository
                .findByExternalId(studentDTO.getUniversityYear().getId());

        if (oldUniversityYear.isPresent()) {
            newUniversityYear.setId(oldUniversityYear.get().getId());
            newUniversityYear.setFaculty(newFaculty);
        }

        newUniversityYear = universityYearRepository.save(newUniversityYear);


        Student newStudent = studentMapper.toEntity(studentDTO);
        Optional<Student> oldStudent = studentRepository.findByExternalId(studentDTO.getId());

        if (oldStudent.isPresent()) {
            newStudent.setId(oldStudent.get().getId());
            newStudent.setUniversityYear(newUniversityYear);
        }

        newStudent = studentRepository.save(newStudent);

    }
}
