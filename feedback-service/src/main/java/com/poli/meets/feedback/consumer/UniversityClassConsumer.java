package com.poli.meets.feedback.consumer;


import com.poli.meets.feedback.domain.Faculty;
import com.poli.meets.feedback.domain.Teacher;
import com.poli.meets.feedback.domain.UniversityClass;
import com.poli.meets.feedback.domain.UniversityYear;
import com.poli.meets.feedback.repository.FacultyRepository;
import com.poli.meets.feedback.repository.TeacherRepository;
import com.poli.meets.feedback.repository.UniversityClassRepository;
import com.poli.meets.feedback.repository.UniversityYearRepository;
import com.poli.meets.feedback.service.mapper.FacultyMapper;
import com.poli.meets.feedback.service.mapper.TeacherMapper;
import com.poli.meets.feedback.service.mapper.UniversityClassMapper;
import com.poli.meets.feedback.service.mapper.UniversityYearMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import poli.meets.coreservice.service.dto.StudentDTO;
import poli.meets.coreservice.service.dto.UniversityClassDTO;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UniversityClassConsumer {

    private final UniversityYearRepository universityYearRepository;

    private final FacultyRepository facultyRepository;

    private final UniversityYearMapper universityYearMapper;

    private final FacultyMapper facultyMapper;

    private final UniversityClassMapper universityClassMapper;

    private final UniversityClassRepository universityClassRepository;

    private final TeacherRepository teacherRepository;

    private final TeacherMapper teacherMapper;

    @KafkaListener(topics = "university_classes", groupId = "feedback-group")
    public void consume(UniversityClassDTO universityClassDTO) throws IOException {

        Faculty newFaculty = facultyMapper.toEntity(universityClassDTO.getUniversityYear().getFaculty());
        Optional<Faculty> oldFaculty = facultyRepository
                .findByExternalId(universityClassDTO.getUniversityYear().getFaculty().getId());

        if (oldFaculty.isPresent()) {
            newFaculty.setId(oldFaculty.get().getId());
        }

        newFaculty = facultyRepository.save(newFaculty);

        UniversityYear newUniversityYear = universityYearMapper.toEntity(universityClassDTO.getUniversityYear());
        newUniversityYear.setFaculty(newFaculty);

        Optional<UniversityYear> oldUniversityYear = universityYearRepository
                .findByExternalId(universityClassDTO.getUniversityYear().getId());

        if (oldUniversityYear.isPresent()) {
            newUniversityYear.setId(oldUniversityYear.get().getId());
        }

        newUniversityYear = universityYearRepository.save(newUniversityYear);

        Teacher newTeacher = teacherMapper.toEntity(universityClassDTO.getTeacher());
        Optional<Teacher> oldTeacher = teacherRepository
                .findByExternalId(universityClassDTO.getUniversityYear().getId());

        if (oldTeacher.isPresent()) {
            newTeacher.setId(oldTeacher.get().getId());
        }

        newTeacher = teacherRepository.save(newTeacher);

        UniversityClass newUniversityClass = universityClassMapper.toEntity(universityClassDTO);
        newUniversityClass.setUniversityYear(newUniversityYear);
        newUniversityClass.setTeacher(newTeacher);

        Optional<UniversityClass> oldStudent = universityClassRepository.findByExternalId(universityClassDTO.getId());

        if (oldStudent.isPresent()) {
            newUniversityClass.setId(oldStudent.get().getId());
        }

        newUniversityClass = universityClassRepository.save(newUniversityClass);
    }

    @KafkaListener(topics = "university-classes-delete", groupId = "feedback-group")
    public void delete(UniversityClassDTO universityClassDTO) throws IOException {
        universityClassRepository.findByExternalId(universityClassDTO.getId())
                .ifPresent(universityClassRepository::delete);
    }
}
