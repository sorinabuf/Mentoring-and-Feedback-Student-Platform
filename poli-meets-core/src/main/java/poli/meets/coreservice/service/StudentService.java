package poli.meets.coreservice.service;

import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import poli.meets.coreservice.client.AuthClient;
import poli.meets.coreservice.domain.Student;
import poli.meets.coreservice.domain.UniversityYear;
import poli.meets.coreservice.repository.StudentRepository;
import poli.meets.coreservice.repository.UniversityYearRepository;
import poli.meets.coreservice.service.dto.StudentDTO;
import poli.meets.coreservice.service.dto.StudentPostDTO;
import poli.meets.coreservice.service.dto.UniversityYearPostDTO;
import poli.meets.coreservice.service.mapper.StudentMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import poli.meets.coreservice.service.util.ImageUtility;
import poli.meets.coreservice.web.rest.errors.BadRequestException;
import poli.meets.coreservice.web.rest.errors.ForbiddenException;

/**
 * Service Implementation for managing {@link Student}.
 */
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    private final AuthClient authClient;

    private final UniversityYearRepository universityYearRepository;


    /**
     * Save a student.
     *
     * @param studentPostDTO the entity to save.
     * @return the persisted entity.
     */
    public StudentDTO save(StudentPostDTO studentPostDTO, Optional<MultipartFile> imageOptional, String token) throws IOException {
        if (!studentPostDTO.getStudentEmail().equals(authClient.getCurrentUser(token).getBody())) {
            throw new ForbiddenException();
        }

        log.debug("Request to save Student : {}", studentPostDTO);
        Student student = studentMapper.toEntity(studentPostDTO);

        UniversityYearPostDTO universityYearPostDTO = studentPostDTO.getUniversityYear();

        UniversityYear universityYear = universityYearRepository
                .findAllByYearAndFacultyIdAndSeries(universityYearPostDTO.getYear(),
                    universityYearPostDTO.getFacultyId(), universityYearPostDTO.getSeries())
                .stream().findAny()
                .orElseThrow(BadRequestException::new);

        if (imageOptional.isPresent()) {
            student.setImage(ImageUtility.compressImage(imageOptional.get().getBytes()));
        }

        student.setUniversityYear(universityYear);

        student = studentRepository.save(student);

        return studentMapper.toDto(student);
    }


    public StudentDTO update(StudentDTO studentDTO, String token) {
        if (!studentDTO.getStudentEmail().equals(authClient.getCurrentUser(token).getBody())) {
            throw new ForbiddenException();
        }

        Student student = studentMapper.toEntity(studentDTO);
        student = studentRepository.save(student);
        return studentMapper.toDto(student);
    }

    /**
     * Get all the students.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<StudentDTO> findAll() {
        log.debug("Request to get all Students");
        return studentRepository.findAll().stream()
            .map(studentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one student by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public StudentDTO findOne(Long id) {
        log.debug("Request to get Student : {}", id);
        return studentRepository.findById(id)
            .map(studentMapper::toDto).orElseThrow(IllegalAccessError::new);
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

    public boolean hasCurrentUserCompletedData(String token) {
        return studentRepository.findByStudentEmail(authClient.getCurrentUser(token).getBody())
                .stream()
                .findAny()
                .isPresent();
    }

    public StudentDTO findCurrentUser(String token) {
        return studentRepository.findByStudentEmail(authClient.getCurrentUser(token).getBody())
                .stream()
                .findAny()
                .map(studentMapper::toDto)
                .orElseThrow(ForbiddenException::new);
    }
}
