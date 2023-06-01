package com.poli.meets.mentorship.service;

import com.poli.meets.mentorship.client.AuthClient;
import com.poli.meets.mentorship.domain.UniversityYear;
import com.poli.meets.mentorship.repository.StudentRepository;
import com.poli.meets.mentorship.domain.Student;
import com.poli.meets.mentorship.repository.UniversityYearRepository;
import com.poli.meets.mentorship.service.dto.StudentDTO;
import com.poli.meets.mentorship.service.dto.StudentPostDTO;
import com.poli.meets.mentorship.service.dto.UniversityYearPostDTO;
import com.poli.meets.mentorship.service.mapper.StudentMapper;

import com.poli.meets.mentorship.service.util.ImageUtility;
import com.poli.meets.mentorship.web.rest.errors.BadRequestException;
import com.poli.meets.mentorship.web.rest.errors.ForbiddenException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Implementation for managing {@link Student}.
 */
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    private final UniversityYearRepository universityYearRepository;

    private final StudentMapper studentMapper;

    private final AuthClient authClient;

    public StudentDTO save(StudentPostDTO studentPostDTO, Optional<MultipartFile> imageOptional, String token) throws IOException {
        log.debug("Request to save Student : {}", studentPostDTO);
        Student student = studentMapper.toEntity(studentPostDTO);

        if (imageOptional.isPresent()) {
            student.setImage(ImageUtility.compressImage(imageOptional.get().getBytes()));
        }

        student = studentRepository.save(student);

        return studentMapper.toDto(student);
    }

    /**
     * Get all the students.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StudentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Students");
        return studentRepository.findAll(pageable)
            .map(studentMapper::toDto);
    }


    /**
     * Get one student by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StudentDTO> findOne(Long id) {
        log.debug("Request to get Student : {}", id);
        return studentRepository.findById(id)
            .map(studentMapper::toDto);
    }

    /**
     * Delete the student by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Student : {}", id);
        studentRepository.deleteById(id);
    }

    public StudentDTO findCurrentUser(String token) {
        return studentRepository.findByStudentEmail(authClient.getCurrentUser(token).getBody())
                .stream()
                .findAny()
                .map(studentMapper::toDto)
                .orElseThrow(ForbiddenException::new);
    }
}
