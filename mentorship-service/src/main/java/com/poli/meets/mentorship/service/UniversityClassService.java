package com.poli.meets.mentorship.service;

import com.poli.meets.mentorship.client.AuthClient;
import com.poli.meets.mentorship.domain.Student;
import com.poli.meets.mentorship.domain.UniversityYear;
import com.poli.meets.mentorship.domain.enumeration.Year;
import com.poli.meets.mentorship.repository.StudentRepository;
import com.poli.meets.mentorship.repository.UniversityClassRepository;
import com.poli.meets.mentorship.domain.UniversityClass;
import com.poli.meets.mentorship.repository.UniversityYearRepository;
import com.poli.meets.mentorship.service.dto.SkillDTO;
import com.poli.meets.mentorship.service.dto.UniversityClassDTO;
import com.poli.meets.mentorship.service.dto.UniversityClassMentorshipDTO;
import com.poli.meets.mentorship.service.mapper.StudentMapper;
import com.poli.meets.mentorship.service.mapper.UniversityClassMapper;

import com.poli.meets.mentorship.web.rest.errors.ForbiddenException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * Service Implementation for managing {@link UniversityClass}.
 */
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class UniversityClassService {

    private final UniversityClassRepository universityClassRepository;

    private final UniversityClassMapper universityClassMapper;

    private final StudentRepository studentRepository;

    private final UniversityYearRepository universityYearRepository;

    private final AuthClient authClient;

    /**
     * Save a universityClass.
     *
     * @param universityClassDTO the entity to save.
     * @return the persisted entity.
     */
    public UniversityClassDTO save(UniversityClassDTO universityClassDTO) {
        log.debug("Request to save UniversityClass : {}", universityClassDTO);
        UniversityClass universityClass = universityClassMapper.toEntity(universityClassDTO);
        universityClass = universityClassRepository.save(universityClass);
        return universityClassMapper.toDto(universityClass);
    }

    /**
     * Get all the universityClasses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UniversityClassDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UniversityClasses");
        return universityClassRepository.findAll(pageable)
            .map(universityClassMapper::toDto);
    }


    /**
     * Get one universityClass by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UniversityClassDTO> findOne(Long id) {
        log.debug("Request to get UniversityClass : {}", id);
        return universityClassRepository.findById(id)
            .map(universityClassMapper::toDto);
    }

    /**
     * Delete the universityClass by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UniversityClass : {}", id);
        universityClassRepository.deleteById(id);
    }

    public List<UniversityClassMentorshipDTO> findAllMentorship(String token) {
        Student student =
                studentRepository.findByStudentEmail(authClient.getCurrentUser(token).getBody())
                .stream()
                .findAny()
                .orElseThrow(ForbiddenException::new);

        List<Year> years =
                Arrays.stream(Year.values())
                        .filter(y -> y.compareTo(student.getUniversityYear().getYear()) < 0)
                        .collect(Collectors.toList());

        List<UniversityYear> universityYears =
                universityYearRepository.findAllByFacultyAndYearIn(student.getUniversityYear().getFaculty(), years);

        return universityClassRepository.findAllByUniversityYearIn(universityYears).stream()
                .map(universityClassMapper::toMentorshipDto)
                .sorted(Comparator.comparing(UniversityClassMentorshipDTO::getName))
                .collect(Collectors.toList());
    }

    public List<UniversityClassDTO> findAllMentors(String token) {
        Student student =
                studentRepository.findByStudentEmail(authClient.getCurrentUser(token).getBody())
                        .stream()
                        .findAny()
                        .orElseThrow(ForbiddenException::new);

        List<Year> years =
                Arrays.stream(Year.values())
                        .filter(y -> y.compareTo(student.getUniversityYear().getYear()) <= 0)
                        .collect(Collectors.toList());

        List<UniversityYear> universityYears =
                universityYearRepository.findAllByFacultyAndYearIn(student.getUniversityYear().getFaculty(), years);

        return universityClassRepository.findAllByUniversityYearIn(universityYears).stream()
                .map(universityClassMapper::toDto)
                .sorted(Comparator.comparing(UniversityClassDTO::getName))
                .collect(Collectors.toList());
    }
}
