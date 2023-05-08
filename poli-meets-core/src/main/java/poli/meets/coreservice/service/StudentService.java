package poli.meets.coreservice.service;

import lombok.AllArgsConstructor;
import poli.meets.coreservice.client.AuthClient;
import poli.meets.coreservice.domain.Student;
import poli.meets.coreservice.repository.StudentRepository;
import poli.meets.coreservice.service.dto.StudentDTO;
import poli.meets.coreservice.service.mapper.StudentMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

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


    /**
     * Save a student.
     *
     * @param studentDTO the entity to save.
     * @return the persisted entity.
     */
    public StudentDTO save(StudentDTO studentDTO) {
        log.debug("Request to save Student : {}", studentDTO);
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
}
