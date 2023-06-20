package com.poli.meets.feedback.consumer;

import com.poli.meets.feedback.domain.Faculty;
import com.poli.meets.feedback.domain.Teacher;
import com.poli.meets.feedback.repository.FacultyRepository;
import com.poli.meets.feedback.repository.TeacherRepository;
import com.poli.meets.feedback.service.mapper.FacultyMapper;
import com.poli.meets.feedback.service.mapper.TeacherMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import poli.meets.coreservice.service.dto.FacultyDTO;
import poli.meets.coreservice.service.dto.StudentDTO;
import poli.meets.coreservice.service.dto.TeacherDTO;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class TeacherConsumer {

    private final TeacherRepository teacherRepository;

    private final TeacherMapper teacherMapper;

    @KafkaListener(topics = "teachers", groupId = "feedback-group")
    public void consume(TeacherDTO teacherDTO) throws IOException {

        Teacher newTeacher = teacherMapper.toEntity(teacherDTO);
        Optional<Teacher> oldTeacher = teacherRepository
                .findByExternalId(teacherDTO.getId());

        if (oldTeacher.isPresent()) {
            newTeacher.setId(oldTeacher.get().getId());
        }

        newTeacher = teacherRepository.save(newTeacher);
    }

    @KafkaListener(topics = "teachers-delete", groupId = "feedback-group")
    public void delete(TeacherDTO teacherDTO) throws IOException {
        teacherRepository.findByExternalId(teacherDTO.getId()).ifPresent(teacherRepository::delete);
    }
}
